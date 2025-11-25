package com.ljh.controller;

import com.ljh.dao.CuponDao;
import com.ljh.pojo.Cupon;
import com.ljh.pojo.Users;
import com.ljh.service.CuponService;
import com.ljh.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/cupon")
public class CuponController {
    @Autowired
    private CuponService cuponService;
    @Autowired
    private CuponDao cuponDao;
    @Autowired
    private UserService userService;

    @RequestMapping("/insert")
    @ResponseBody
    public Result insert(@RequestBody Cupon cupon) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(cupon.getUserLimitString() == null || "".equals(cupon.getUserLimitString())) {
            cupon.setUserLimit(LocalDate.now().plusYears(-1000));
        }else {
            LocalDate userLimit = LocalDate.parse(cupon.getUserLimitString(),dtf);
            cupon.setUserLimit(userLimit);
        }
        LocalDate endTime = LocalDate.parse(cupon.getEndTimeString());
        cupon.setEndTime(endTime);
        if(cuponService.insert(cupon)){
            return new Result(null,Status.CUPONADD_SUCCESS,"添加成功");
        }else {
            return new Result(null,Status.CUPONDADD_ERROR,"添加失败，请稍后再试");
        }
    }

    @RequestMapping("/findByCondition")
    @ResponseBody
    public List<Cupon> findByCondition(@RequestBody Cupon cupon, HttpSession session) {
       String userName = (String)session.getAttribute("userName");
        Users users = userService.selectByUserName(userName);
        LocalDate registerTime  = users.getRegisterTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        cupon.setRegisterTime(registerTime);
        return cuponService.selectByCondition(cupon);
    }

    @RequestMapping("/selectUseful")
    @ResponseBody
    public List<Cupon> selectUseful(@RequestBody Cupon cupon,HttpSession session) {
        String userName = (String)session.getAttribute("userName");
        Users users = userService.selectByUserName(userName);
        cupon.setUserId(users.getUserId());
        return cuponService.selectUseful(cupon);
    }

    @RequestMapping("/selectNewUser")
    @ResponseBody
    public List<Cupon> selectNewUser(HttpSession session,int cuponId) {
        String userName = (String)session.getAttribute("userName");
        Users users = userService.selectByUserName(userName);
        return  cuponService.selectNewUser(users.getUserId(),cuponId);
    }

    @RequestMapping("/isNewUser")
    @ResponseBody
    public int isNewUser(HttpSession session,int cuponId) {
        String userName = (String)session.getAttribute("userName");
        Users users = userService.selectByUserName(userName);
        return  cuponDao.isNewUser(users.getUserId(),cuponId);
    }

    @RequestMapping("/insertCuponUser")
    @ResponseBody
    public Result insertCuponUser(HttpSession session,int cuponId) {
        String userName = (String)session.getAttribute("userName");
        Users users = userService.selectByUserName(userName);
        Cupon cupon = new Cupon();
        cupon.setCuponId(cuponId);
        cupon.setUserId(users.getUserId());
        if (cuponService.insertCuponUser(cupon)) {
            cuponService.updateCuponAmount(cuponId);
            return new Result(null,Status.CUPONINSERT_SUCCESS,"领取成功");
        }else {
            return new Result(null,Status.CUPONINSERT_ERROR,"领取失败");
        }
    }

    @RequestMapping("/selectAllCupon")
    @ResponseBody
    public List<Cupon> selectAllCupon() {
        return cuponService.selectAllCupon();
    }

    @RequestMapping("updateCuponEndTime")
    @ResponseBody
    public Result updateCuponEndTime(@RequestBody Cupon cupon) {
        LocalDate endTime = LocalDate.parse(cupon.getEndTimeString());
        cupon.setEndTime(endTime);
        if(cuponService.updateCuponEndTime(cupon)){
            if (LocalDate.now().isBefore(endTime)){
                cuponDao.updateByCuponId(cupon.getCuponId());
            }
            return new Result(null,Status.CUPONUPDATE_SUCCESS,"修改成功");
        }
       else {
           return new Result(null,Status.CUPONUPDATE_ERROR,"修改失败");
        }
    }

    @RequestMapping("/deleteCupon")
    @ResponseBody
    public Result deleteCupon(int cuponId) {
        if(cuponService.deleteCupon(cuponId)) {
            return new Result(null,Status.CUPONDELETE_SUCCESS,"删除成功");
        }else {
            return new Result(null,Status.CUPONDELETE_ERROR,"删除失败");
        }
    }

    @RequestMapping("/selectByCuponStatus")
    @ResponseBody
    public List<Cupon> selectByCuponStatus(String cuponStatus) {
        return cuponDao.selectByCuponStatus(cuponStatus);
    }

    @RequestMapping("updateCuponUse")
    @ResponseBody
    public boolean updateCuponUse(String orderId,HttpSession session,int cuponId) {
        String userName = (String)session.getAttribute("userName");
        Users users = userService.selectByUserName(userName);
        int userId = users.getUserId();
       return cuponDao.updateCuponUse(orderId,userId,cuponId)>0;
    }
}
