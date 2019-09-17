package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.doc.ESCourse;
import cn.wangningbo.hrm.query.ESCourseQuery;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author wangningbo
 * @date 2019/09/06
 */
@Component
public class EsCourseClientHystrixFallbackFactory implements FallbackFactory<ESCourseClient> {

    @Override
    public ESCourseClient create(Throwable throwable) {
        return new ESCourseClient() {
            @Override
            public AjaxResult save(ESCourse esCourse) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public ESCourse get(Long id) {
                return null;
            }

            @Override
            public List<ESCourse> list() {
                return null;
            }

            @Override
            public PageList<ESCourse> json(ESCourseQuery query) {
                return null;
            }

            @Override
            public AjaxResult batchSave(List<ESCourse> esCourseList) {
                return null;
            }

            @Override
            public void batchDel(List<ESCourse> esCourseList) {

            }

            @Override
            public PageList<Map<String, Object>> query(Map<String, Object> params) {
                return null;
            }
        };
    }
}
