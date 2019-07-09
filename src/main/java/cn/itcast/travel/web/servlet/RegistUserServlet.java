package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取数据（因为已经提前处理编码了）
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        User user = new User();
        try{
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (InvocationTargetException e){
            e.printStackTrace();
        }



        //3.调用Service完成注册
        UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo resultInfo = new ResultInfo();
        //4.响应结果
        if (flag){
            //注册成功
            resultInfo.setFlag(true);
        }else{
            //注册失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
        }


        //将resultInfo对象序列化成json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);


        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);





    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
