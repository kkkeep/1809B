package jy.com.libcustomview.contactlist;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.pinyinhelper.PinyinMapDict;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * created by taofu on 2019-05-14
 **/
public class ContactUtils {

    // 号码
    private final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    private final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;

    //联系人提供者的uri
    private static final Uri PHONE_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    static {
        Pinyin.init(Pinyin.newConfig().with(new PinyinMapDict() {
            @Override
            public Map<String, String[]> mapping() {
                Map<String,String[]> map = new HashMap<>();
                    map.put("曾", new String[]{"zeng"});

                    return map;
            }
        }));
    }

    /**
     * 获取联系人列表
     * @param context Context 对象，最好传入 application 的 context 对象
     * @return 如果有手机里面存在联系人则返回联系人列表，否则返回null。注意读取联系人需要权限
     */
    public static ArrayList<Contact> getPhoneContactList(Context context) {

        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(PHONE_URI, new String[]{NUM, NAME}, null, null, null);
        if (cursor == null || cursor.getCount() < 1) {
            return null;
        }
        ArrayList<Contact> contacts = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            Contact contact = new Contact(cursor.getString(cursor.getColumnIndex(NAME)), cursor.getString(cursor.getColumnIndex(NUM)));
            contacts.add(contact);

        }
        cursor.close();
        return contacts;
    }

    public static String hanyu2pinyin(String hanyu){
        if(TextUtils.isEmpty(hanyu))
            return null;


        //String.valueOf(c).matches("[\u4e00-\u9fa5]")
        // 添加中文城市词典
       //return HanziNameToPinyin.getPinYin(hanyu," ");
       return Pinyin.toPinyin(hanyu, " ");
    }
}
