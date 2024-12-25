package org.kiteseven.bms_server.service.Impl;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kiteseven.bms_common.exception.BaseException;
import org.kiteseven.bms_common.result.PageResult;
import org.kiteseven.bms_common.utils.BicycleStatusUtil;
import org.kiteseven.bms_common.utils.JDBCUtil;
import org.kiteseven.bms_pojo.dto.BicycleDTO;
import org.kiteseven.bms_pojo.dto.BicycleUpdateDTO;
import org.kiteseven.bms_pojo.dto.BikeRentDTO;
import org.kiteseven.bms_pojo.entity.Bicycles;
import org.kiteseven.bms_pojo.vo.BicyclesVO;
import org.kiteseven.bms_pojo.vo.RentalVO;
import org.kiteseven.bms_pojo.vo.UserVO;
import org.kiteseven.bms_server.mapper.BicycleMapper;
import org.kiteseven.bms_server.mapper.RentalMapper;
import org.kiteseven.bms_server.mapper.UserMapper;
import org.kiteseven.bms_server.service.BicyclesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public  class BicyclesServiceImpl implements BicyclesService{
    @Autowired
    BicycleMapper bicycleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RentalMapper rentalMapper;
    @Override
    public void addBicycles(BicycleDTO bicycleDTO) {
        bicycleMapper.addBicycles(bicycleDTO);
    }

    @Override
    public PageResult getAllBicycles() {
        List<BicyclesVO> list=bicycleMapper.getBicycles();
        Long total =bicycleMapper.getAllBicyclesCount();
        return new PageResult(total,list);
    }

    @Override
    @Transactional
    public void updateBicycle(BicycleUpdateDTO bicycleUpdateDTO) {
        Bicycles bicycles=bicycleMapper.getBicycle(bicycleUpdateDTO.getBicycleId());
        log.info(":{}",bicycles);
        if(bicycles.getStatus()==2){
            throw new BaseException("无法修改！该车租借中！");
        }
        bicycleMapper.updateBicycle(bicycleUpdateDTO);
    }

    @Override
    public void deleteBicycle(Integer id) {
        Bicycles bicycles=bicycleMapper.getBicycle(id);
        if(bicycles==null){
            throw new BaseException("该单车不存在");
        }
        //这里还需要添加一个判断单车是否租出，租出不能删除
        if(bicycles.getStatus()==2){
            throw new BaseException("该单车出租中！");
        }
        bicycleMapper.deleteBicycle(id);
    }

    @Override
    public PageResult getAvailableBike() {
        List<BicyclesVO> list=bicycleMapper.getAvailableBike();
        Long total=bicycleMapper.getAllBicyclesCount();
        return new PageResult(total,list);
    }

    @Override
    @Transactional
    public void rentBike(BikeRentDTO bikeRentDTO) {
        LocalDateTime time=LocalDateTime.now();
        bikeRentDTO.setRentTime(time);
        Bicycles bicycles=bicycleMapper.getBicycle(bikeRentDTO.getBicycleId());
        if(bicycles==null){
            throw new BaseException("该单车不存在！");
        }
        UserVO user=userMapper.getUserData(bikeRentDTO.getUserId());
        if(user.getCreditStatus()==0){
            throw new BaseException("该用户为失信用户，无法租借单车");
        }
        if(bicycles.getStatus()!=1){
            throw new BaseException("该单车暂时无法租借");
        }
        bikeRentDTO.setRentalFee(bicycles.getRentalFree());
        bicycles.setStatus(2);
        int rentalCount = bicycles.getRentalCount()+1;
        bicycles.setRentalCount(rentalCount);
        BicycleUpdateDTO bicycleDTO =new BicycleUpdateDTO();
        BeanUtils.copyProperties(bicycles,bicycleDTO);
        rentalMapper.RentalBike(bikeRentDTO);
        bicycleMapper.updateBicycle(bicycleDTO);
    }

    @Override
    public PageResult getOrders() {
        List<RentalVO> list=rentalMapper.getRentals();
        Long total=rentalMapper.getRentalsCount();
        return new PageResult(total,list);
    }

    @Override
    @Transactional
    public void completeOrder(Integer rentalId) {
        LocalDateTime returnTime =LocalDateTime.now();
        RentalVO rentalVO=rentalMapper.getRentalData(rentalId);
        Duration totalTime=Duration.between(returnTime,rentalVO.getRentTime());
        rentalMapper.completeOrder(rentalId,returnTime);

        BicycleUpdateDTO bicycles=new BicycleUpdateDTO();
        bicycles.setBicycleId(rentalVO.getBicycleId());
        bicycles.setStatus(1);

        bicycleMapper.updateBicycle(bicycles);
    }

    @Override
    @Transactional
    public void cancelOrder(Integer rentalId) {
         LocalDateTime returnTime =LocalDateTime.now();
         rentalMapper.cancelOrder(rentalId,returnTime);
         RentalVO rentalVO=rentalMapper.getRentalData(rentalId);
         BicycleUpdateDTO bicycles=new BicycleUpdateDTO();
         bicycles.setBicycleId(rentalVO.getBicycleId());
         bicycles.setStatus(1);
         bicycleMapper.updateBicycle(bicycles);
    }

    @Override
    public PageResult searchBike(String model,String location, Integer status) {
        // 初始查询语句
        StringBuilder sql = new StringBuilder("SELECT * FROM bicycles WHERE 1=1");

        // 存放查询参数
        List<Object> params = new ArrayList<>();

        // 根据条件动态拼接查询
        if (model != null && !model.trim().isEmpty()) {
            sql.append(" AND model LIKE ?");
            params.add("%" + model + "%");
        }
        if (location != null && !location.trim().isEmpty()) {
            sql.append(" AND location LIKE ?");
            params.add("%" + location + "%");
        }
        if (status != null) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        List<BicyclesVO> bicyclesVOList=new ArrayList<>();
        try(Connection connection=JDBCUtil.getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(sql.toString())
        ) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            try(ResultSet resultSet=preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    BicyclesVO bicyclesVO=new BicyclesVO(
                            resultSet.getInt("bicycle_id"),
                            resultSet.getString("model"),
                            resultSet.getString("location"),
                            resultSet.getInt("status"),
                            resultSet.getDouble("rental_free"),
                            resultSet.getInt("rental_count"),
                            resultSet.getString("bicycle_image"));
                    bicyclesVOList.add(bicyclesVO);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Long total= (long) bicyclesVOList.size();
        return new PageResult(total,bicyclesVOList);
    }

    @Override
    public void exportBicycleData(HttpServletResponse response) {
        // 查询所有单车数据
        List<BicyclesVO> bicyclesList = bicycleMapper.exportBicyclesDataTop100();
        // 获取 Excel 模板文件
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("单车信息报表.xlsx");

        try {
            // 基于模板文件创建一个新的 Excel 文件
            XSSFWorkbook excel = new XSSFWorkbook();
            XSSFSheet sheet = excel.createSheet("单车信息");

            // 创建标题行
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("编号");
            headerRow.createCell(1).setCellValue("型号");
            headerRow.createCell(2).setCellValue("位置");
            headerRow.createCell(3).setCellValue("状态");
            headerRow.createCell(4).setCellValue("租赁价格");
            headerRow.createCell(5).setCellValue("租赁次数");
            // 获取表格文件的 Sheet 页

            // 写入数据行
            for (int i = 0; i < bicyclesList.size(); i++) {
                BicyclesVO bicycle = bicyclesList.get(i);
                XSSFRow row = sheet.createRow(i + 1);

                row.createCell(0).setCellValue(bicycle.getBicycleId());
                row.createCell(1).setCellValue(bicycle.getModel() != null ? bicycle.getModel() : "未知型号");
                row.createCell(2).setCellValue(bicycle.getLocation() != null ? bicycle.getLocation() : "未知位置");
                row.createCell(3).setCellValue(BicycleStatusUtil.getStatusText(bicycle.getStatus()));
                row.createCell(4).setCellValue(bicycle.getRentalFree() != null ? bicycle.getRentalFree() : 0);
                row.createCell(5).setCellValue(bicycle.getRentalCount() != null ? bicycle.getRentalCount() : 0);
                row.createCell(6).setCellValue(bicycle.getBicycleImage() != null ? bicycle.getBicycleImage() : "无图片");
            }
            // 通过输出流将 Excel 文件写入客户端浏览器
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);

            // 关闭资源
            out.close();
            excel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
