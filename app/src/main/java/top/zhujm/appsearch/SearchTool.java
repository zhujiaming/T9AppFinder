package top.zhujm.appsearch;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import top.zhujm.appsearch.search.SearchTask;
import top.zhujm.appsearch.utils.Utils;


public class SearchTool {

    private static final String TAG = "SearchTool";

//    public static final SparseArray<String[]> KEYS = new SparseArray<>();
//    static {
//        KEYS.append(-2, new String[]{"#"});
//        KEYS.append(-1, new String[]{"*"});
//        KEYS.append(0, new String[]{"0"});
//        KEYS.append(1, new String[]{"1"});
//        KEYS.append(2, new String[]{"2", "A", "B", "C"});
//        KEYS.append(3, new String[]{"3", "D", "E", "F"});
//        KEYS.append(4, new String[]{"4", "G", "H", "I"});
//        KEYS.append(5, new String[]{"5", "J", "K", "L"});
//        KEYS.append(6, new String[]{"6", "M", "N", "O"});
//        KEYS.append(7, new String[]{"7", "P", "Q", "R", "S"});
//        KEYS.append(8, new String[]{"8", "T", "U", "V"});
//        KEYS.append(9, new String[]{"9", "W", "X", "Y", "Z"});
//    }

    public static Map<String, List<AppInfo>> KEYAPPS = new HashMap<>();

//    private Map<String, List<AppInfo>> cacheApps = new HashMap<>();

    public String perKey = "";
    int appListLen;
    private OnResultListener listener = new OnResultListener() {
        @Override
        public void onResult(List<AppInfo> datas) {
            if (resultHandler != null) {
                Message.obtain(resultHandler, SearchTask.MSG_RESULT, datas).sendToTarget();
            }
        }
    };
    private Handler resultHandler;

//    public SearchTool(OnResultListener listener) {
//        this.listener = listener;
//    }

    public SearchTool(Handler resultHandler) {
        this.resultHandler = resultHandler;
    }

    public void prepareApps(Context context) {
        KEYAPPS = Utils.getAllApps(context);
    }

    /**
     * 重置
     */
    public void reset() {
        perKey = "";
        appListLen = 0;
        listener.onResult(Collections.EMPTY_LIST);
    }

    /**
     * 回退查找
     */
    public void rollbackSearch() {
        if (!TextUtils.isEmpty(perKey)) {
            perKey = perKey.substring(0, perKey.length() - 1);
            if (TextUtils.isEmpty(perKey)) {
                listener.onResult(Collections.<AppInfo>emptyList());
            } else {
                searchByKey();
            }
        }
    }

    /**
     * 输入查找
     *
     * @param key
     */
    public void enterToSearch(int key) {

        if (key == 0) {
            //实际为清空输入
            reset();
        } else {
            //如果app列表的长度为0，且已输入的key不为空，则忽略后面的输入
            if (appListLen == 0 && !TextUtils.isEmpty(perKey)) {
                return;
            }
            perKey += key;
            searchByKey();
        }
    }

    private void searchByKey() {
        long start = SystemClock.uptimeMillis();
        List<AppInfo> datas = new ArrayList<>();
        Log.i(TAG, "searchByKey|" + perKey);
        Map<String, List<AppInfo>> tempAppList = KEYAPPS;
//        if (cacheApps.size() > 0) {
//            tempAppList = new HashMap<>();
//            tempAppList.putAll(cacheApps);
//        }
        Set<Map.Entry<String, List<AppInfo>>> entries = tempAppList.entrySet();
        Iterator<Map.Entry<String, List<AppInfo>>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<AppInfo>> next = iterator.next();
            String key = next.getKey();
            List<AppInfo> appInfos = next.getValue();
            if (key.startsWith(perKey)) {
                datas.addAll(appInfos);
//                if (cacheApps.containsKey(key)) {
//                    cacheApps.get(key).addAll(appInfos);
//                }else{
//                    cacheApps.put(key, appInfos);
//                }
            }
        }
        appListLen = datas.size();
        Log.i(TAG, "searchByKey|" + perKey + "|cost:" + (SystemClock.uptimeMillis() - start) + "ms");
        listener.onResult(datas);
    }

    public interface OnResultListener {
        void onResult(List<AppInfo> datas);
    }
}
