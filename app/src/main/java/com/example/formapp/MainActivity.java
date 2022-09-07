package com.example.formapp;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.formapp.utils.form.FormUtil;

public class MainActivity extends AppCompatActivity {
    private String json =
            "[" +
                    "{\"key\":\"name\",\"type\":\"text\",\"title\":\"姓名\",\"value\":\"赵四赵四赵能获取用户信息吗？调用这个接口赵四赵四赵能获取用户信息吗？调用这个接口\"}," +
                    "{\"key\":\"date1\",\"type\":\"date\",\"title\":\"日期1\",\"value\":\"\"}," +
                    "{\"key\":\"date2\",\"type\":\"date\",\"title\":\"日期2\",\"value\":\"\"}," +
                    "{\"key\":\"age\",\"type\":\"select\",\"title\":\"年龄\",\"defaultSelect\":1,\"options\":[{\"value\":\"20\",\"id\":\"a001\"},{\"value\":\"30\",\"id\":\"a002\"},{\"value\":\"50\",\"id\":\"a003\"}]}," +
                    "{\"key\":\"like\",\"type\":\"edit\",\"title\":\"爱好\",\"value\":\"足球\",\"canEdit\":true}," +
                    "{\"key\":\"citizenship\",\"type\":\"select\",\"title\":\"国籍\",\"options\":[{\"value\":\"中国\",\"id\":\"b001\"},{\"value\":\"俄罗斯\",\"id\":\"b002\"},{\"value\":\"意大利\",\"id\":\"b003\"}]}," +
                    "{\"key\":\"file1\",\"type\":\"image\",\"title\":\"附件1\",\"canEdit\":true,\"imageMaxSize\":3,\"imageList\":[" +
                    "{\"imageUrl\":\"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp09%2F210F2130512J47-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659518735&t=0f520dda21c517cb0d04ae3445ee41bf\",\"imageName\":\"c001\"}," +
                    "{\"imageUrl\":\"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F1011%2F010QG05111%2F1F10Q05111-3.jpg&refer=http%3A%2F%2Fpic.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659861568&t=fb2d938608b8b82f10f6030a41bd75b9\",\"imageName\":\"c003\"}]}," +
                    "{\"key\":\"file2\",\"type\":\"image\",\"title\":\"附件2\",\"canEdit\":true,\"imageMaxSize\":6,\"imageList\":[" +
                    "{\"imageUrl\":\"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp09%2F210F2130512J47-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659518735&t=0f520dda21c517cb0d04ae3445ee41bf\",\"imageName\":\"d001\"}," +
                    "{\"imageUrl\":\"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp09%2F210F2130512J47-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659518735&t=0f520dda21c517cb0d04ae3445ee41bf\",\"imageName\":\"d001\"}," +
                    "{\"imageUrl\":\"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp09%2F210F2130512J47-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659518735&t=0f520dda21c517cb0d04ae3445ee41bf\",\"imageName\":\"d001\"}," +
                    "{\"imageUrl\":\"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp09%2F210F2130512J47-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659518735&t=0f520dda21c517cb0d04ae3445ee41bf\",\"imageName\":\"d001\"}," +
                    "{\"imageUrl\":\"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1113%2F052420110515%2F200524110515-2-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659861549&t=5b66dc3997552c0b27970e07656ec22c\",\"imageName\":\"d002\"}," +
                    "{\"imageUrl\":\"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F1011%2F010QG05111%2F1F10Q05111-3.jpg&refer=http%3A%2F%2Fpic.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659861568&t=fb2d938608b8b82f10f6030a41bd75b9\",\"imageName\":\"d003\"}]}," +
                    "{\"key\":\"date3\",\"type\":\"date\",\"title\":\"日期3\",\"value\":\"\"}" +
                    "]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout formLl = findViewById(R.id.form_ll);
        setTitle("表单");
        FormUtil formUtil = new FormUtil(json, formLl, this);
    }
}