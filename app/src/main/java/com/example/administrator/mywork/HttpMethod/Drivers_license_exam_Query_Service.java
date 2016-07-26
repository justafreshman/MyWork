package com.example.administrator.mywork.HttpMethod;

import com.example.administrator.mywork.bean.HttpResult;
import com.example.administrator.mywork.bean.driver;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/2.
 * 作者：wu
 */
public interface Drivers_license_exam_Query_Service {
    /**
     错误码	说明
     218301	暂无符合车型或科目
     * */
    /**
    请求参数说明：
    名称	类型	必填	说明
    key	string	是	您申请的appKey
    subject	int	是	选择考试科目类型，1：科目1；4：科目4
    model	string	是	驾照类型，可选择参数为：c1,c2,a1,a2,b1,b2；当subject=4时可省略
    testType	string	否	测试类型，rand：随机测试（随机100个题目），order：顺序测试（所选科目全部题目）
     */

    /**
     返回参数说明：
     名称	类型	说明
     error_code	int	返回状态码
     reason	string	返回原因
     result	string	题目内容
     请注意:	当四个选项都为空的时候表示判断题,item1:正确 item2:错误,请开发者自行判断!
     */
    @GET("jztk/query")
    Observable<HttpResult<List<driver>>> getQuestion(@Query("subject") int subject,@Query("model") String model,@Query("key") String appkey,@Query("testType") String testType);

}
