package com.fanwe.o2o.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.model.SDTaskRunnable;
import com.fanwe.library.utils.SDCache;
import com.fanwe.library.view.SDSlidingButton;
import com.fanwe.o2o.R;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.event.ERefreshAddressDialog;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppGroupPurListIndexActModel;
import com.fanwe.o2o.model.App_RegionListActModel;
import com.fanwe.o2o.model.RegionModel;
import com.fanwe.o2o.model.ShippingAddressModel;
import com.fanwe.o2o.work.AppRuntimeWorker;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * User: ldh (394380623@qq.com)
 * Date: 2017-03-02
 * Time: 15:51
 * 功能:
 */
public class AddAddressDialog extends SDDialogCustom {

    @ViewInject(R.id.et_address_name)
    private EditText name;
    @ViewInject(R.id.dialog_address_back)
    private Button back;
    @ViewInject(R.id.dialog_address_complete)
    private TextView complete;

    @ViewInject(R.id.et_address_phone)
    private EditText phone;

    @ViewInject(R.id.et_address_doorplate)
    private EditText doorplate;

    @ViewInject(R.id.dialog_address_default)
    private SDSlidingButton is_default;

    @ViewInject(R.id.dialog_address_tv)
    private TextView address;

    @ViewInject(R.id.act_add_delivery_address_tv_delivery)
    private TextView act_add_delivery_address_tv_delivery;

    private OptionsPickerView mPickerCity;//城市选择器
    private ArrayList<RegionModel> mListProvince;//省份集合
    private ArrayList<ArrayList<RegionModel>> mListCity;//城市集合
    private ArrayList<ArrayList<ArrayList<RegionModel>>> mListCounty;//区集合

    private String county;
    private String city;//进入城市编辑页面传递的城市
    private String province;//进入省份编辑页面传递的省份


    private ShippingAddressModel mModel;
    private boolean b = false;  //用于判断设置默认按钮状态 false为未选择
    private boolean first = true; //用于判断是否初始即为默认

    private static final int FLAG_HOMETOWN = 0X006;//家乡对应的flag
    private static final int FLAG_PROVINCE = 0X007;//省份对应的flag
    private static final int FLAG_CITY = 0X008;//省份对应的flag
    private static final int FLAG_COUNTY = 0X009;//区对应的flag

    private final String KEY_CITY = "city";//城市对应的key
    private final String KEY_PROVINCE = "province";//省份对应的key
    private final String KEY_COUNTY = "county";//区对应的key

    private String homeTown;

    public AddAddressDialog(Activity activity) {
        super(activity);
        initData();
    }

    public AddAddressDialog(Activity activity, ShippingAddressModel model) {
        super(activity);
        this.mModel = model;
        initData();
    }

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.dialog_add_address);
        setFullScreen();
        x.view().inject(this, getContentView());
    }


    private void initData() {
        act_add_delivery_address_tv_delivery.setOnClickListener(this);
        back.setOnClickListener(this);
        complete.setOnClickListener(this);
        is_default.setOnClickListener(this);
        if (mModel != null) {
            name.setText(mModel.getConsignee());
            phone.setText(mModel.getMobile());
            address.setText(mModel.getStreet());
            doorplate.setText(mModel.getAddress());
            if (mModel.getIs_default() == 0) {
                is_default.setSelected(false);
            } else if (mModel.getIs_default() == 1) {
                is_default.setSelected(true);
            }
            is_default.setSelectedChangeListener(new SDSlidingButton.SelectedChangeListener() {
                @Override
                public void onSelectedChange(SDSlidingButton view, boolean selected) {
                    if (mModel.getIs_default() == 1) {
                        if (!selected) {
                            is_default.setSelected(true);
                            b = true;
                            if (first) {
                                first = false;
                                return;
                            } else {
                                Toast.makeText(getContext(), "无法取消默认", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            b = !b;
                        }
                    } else {
                        b = !b;
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.dialog_address_back:
                dismiss();
                break;
            case R.id.dialog_address_complete:
                if (TextUtils.isEmpty(name.getText())) {
                    Toast.makeText(getContext(), "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone.getText())) {
                    Toast.makeText(getContext(), "电话不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address.getText())) {
                    Toast.makeText(getContext(), "地址不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(doorplate.getText())) {
                    Toast.makeText(getContext(), "详细地址不能为空", Toast.LENGTH_SHORT).show();
                } else {
//                    if (mModel != null) {
//                        if (b) {
//                            mModel.setIs_default(1);
//                        } else {
//                            mModel.setIs_default(0);
//                        }
//                        CommonInterface.requestEditShippingAddressListIndex(mModel.getAddress(), mModel.getId(), mModel.getIs_default(), mModel.getConsignee(),
//                                mModel.getMobile(), mModel.getStreet(), mModel.getDoorplate(), mModel.getRegion_lv1(), mModel.getRegion_lv2(),
//                                mModel.getRegion_lv3(), mModel.getRegion_lv4(), new AppRequestCallback<AppGroupPurListIndexActModel>() {
//                                    @Override
//                                    protected void onSuccess(SDResponse sdResponse) {
//                                        ERefreshAddressDialog event = new ERefreshAddressDialog();
//                                        SDEventManager.post(event);
//                                        Toast.makeText(getContext(), "编辑保存成功", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                    } else {
//                        CommonInterface.requestAddShippingAddressListIndex(mModel.getAddress(), mModel.getIs_default(), mModel.getConsignee(),
//                                mModel.getMobile(), mModel.getStreet(), mModel.getDoorplate(),  mModel.getRegion_lv1(), mModel.getRegion_lv2(),
//                                mModel.getRegion_lv3(), mModel.getRegion_lv4(), new AppRequestCallback<AppGroupPurListIndexActModel>() {
//                                    @Override
//                                    protected void onSuccess(SDResponse sdResponse) {
//                                        ERefreshAddressDialog event = new ERefreshAddressDialog();
//                                        SDEventManager.post(event);
//                                        Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                    }
                    dismiss();
                }
                break;
            case R.id.dialog_address_default:
                if (mModel != null) {
                    if (mModel.getIs_default() == 0) {
                        CommonInterface.requestDefaultShippingAddressListIndex(mModel.getId(), new AppRequestCallback<AppGroupPurListIndexActModel>() {
                            @Override
                            protected void onSuccess(SDResponse sdResponse) {
                                is_default.setSelected(true);
                            }
                        });
                    } else if (mModel.getIs_default() == 1) {
                        Toast.makeText(getContext(), "无法取消默认", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    if (b) {
                        CommonInterface.requestDefaultShippingAddressListIndex(mModel.getId(), new AppRequestCallback<AppGroupPurListIndexActModel>() {
                            @Override
                            protected void onSuccess(SDResponse sdResponse) {
                                is_default.setSelected(true);
                                b = true;
                            }
                        });
                    } else {
                        b = false;
                        return;
                    }
                }
                break;
            case R.id.act_add_delivery_address_tv_delivery:
                if (mPickerCity == null) {
                    initCityPicker();
                } else {
                    mPickerCity.show();
                }
                break;
        }
    }


    /**
     * 初始化城市选择器
     */
    private void initCityPicker() {
        mPickerCity = new OptionsPickerView(getOwnerActivity());
        mListProvince = new ArrayList<>();
        mListCity = new ArrayList<>();
        mListCounty = new ArrayList<>();
        mPickerCity.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                province = mListProvince.get(options1).getName();
                city = mListCity.get(options1).get(option2).getName();
                county = mListCounty.get(options1).get(option2).get(options3).getName();

//                String modifyText = getHomeTown(province,city,county);
//                if(! TextUtils.equals(modifyText,homeTown)) {
//                    mModifyElement.put(getKey(FLAG_PROVINCE),province);
//                    mModifyElement.put(getKey(FLAG_CITY),city);
//                    if(province.equals(mOriginElement.get(getKey(FLAG_PROVINCE)))) {
//                        mModifyElement.remove(getKey(FLAG_PROVINCE));
//                    }
//                    if(city.equals(mOriginElement.get(getKey(FLAG_CITY)))) {
//                        mModifyElement.remove(getKey(FLAG_CITY));
//                    }
//                    setText(FLAG_HOMETOWN,modifyText);
//                }
            }
        });
        mPickerCity.setCancelable(true);
        checkRegionVersion();
    }


    /**
     * 获取flag对应的在容器Map中的key
     * @param flag
     * @return
     */
    private String getKey(int flag) {
        switch(flag) {
            case FLAG_CITY :
                return KEY_CITY;
            case FLAG_PROVINCE :
                return KEY_PROVINCE;
            case FLAG_COUNTY :
                return KEY_COUNTY;
            default:
                return "";
        }
    }

    /**
     * 将修改后的文本显示出来
     * @param flag
     * @param modifyText
     */
    private void setText(int flag,String modifyText) {
        switch(flag) {
            case FLAG_HOMETOWN :
                homeTown = modifyText;
                act_add_delivery_address_tv_delivery.setText(modifyText);
                break;
            default:
                break;
        }
    }


    /**
     * 格式化 家乡
     * @param province
     * @param city
     * @param county
     * @return  “省份 城市”
     */
    private String getHomeTown(String province,String city,String county) {
        if(TextUtils.isEmpty(province) && TextUtils.isEmpty(city)&& TextUtils.isEmpty(county)) {
            return "-请选择- -请选择- -请选择-";
        }
        return province+" "+city+" "+county;
    }

    /**
     * 检查地区版本、更新保存数据
     */
    private void checkRegionVersion() {
        App_RegionListActModel regionActModel = AppRuntimeWorker.getRegionListActModel();
        if (regionActModel == null) {
            CommonInterface.requestDeliveryRegion(new AppRequestCallback<App_RegionListActModel>() {
                @Override
                protected void onSuccess(SDResponse resp) {
                    if (actModel.isOk()) {
//                        // 保存数据到数据库成功
//                        if (Region_confModelDao.getInstance().deleteOldAndSaveNew(actModel.getRegion_list()))
//                        {
//                            AppConfig.setRegionConfVersion(serverVersion);
//                            showSelectRegionDialog();
//                        }

                        SDCache.setObject(actModel);
                        SDConfig.getInstance().setInt(R.string.config_region_version, actModel.getRegion_versions());
                        handleCityData(actModel.getRegion_list());
                    }
                }

                @Override
                protected void onError(SDResponse resp) {
                    super.onError(resp);
                }
            });
        } else {
            handleCityData(regionActModel.getRegion_list());
        }
    }

    /**
     * 分类省份及其对应的城市集合
     *
     * @param regionModelArrayList
     */
    private void handleCityData(final ArrayList<RegionModel> regionModelArrayList) {
        SDHandlerManager.getBackgroundHandler().post(new SDTaskRunnable<String>() {
            @Override
            public String onBackground() {
                initCityData(regionModelArrayList);
                return null;
            }

            @Override
            public void onMainThread(String result) {
                mPickerCity.setPicker(mListProvince, mListCity,mListCounty, true);
                mPickerCity.setCyclic(false);
                mPickerCity.setSelectOptions(getProvincePosition(), getCityPosition(getProvincePosition()), getCountyPosition(getProvincePosition(), getCityPosition(getProvincePosition())));//默认选中
                mPickerCity.show();
            }
        });
    }

    /**
     * 初始化城市数据
     *
     * @param regionModelArrayList
     */
    private void initCityData(ArrayList<RegionModel> regionModelArrayList) {
        Iterator<RegionModel> it = regionModelArrayList.iterator();
        while (it.hasNext()) {
            RegionModel item = it.next();
            if (item.getRegion_level() == 2) {
                mListProvince.add(item);
                it.remove();
            }
        }

        if (mListProvince != null) {
            for (RegionModel model : mListProvince) {
                ArrayList<RegionModel> listCity = new ArrayList<>();
                for (RegionModel item : regionModelArrayList) {
                    if (item.getPid() == model.getId()) {
                        listCity.add(item);
                    }
                }
                mListCity.add(listCity);
            }
        }

        if (mListCity != null) {
            for (ArrayList<RegionModel> citys : mListCity) {
                if (citys != null)
                {
                    ArrayList<ArrayList<RegionModel>> listQuIndex = new ArrayList<>();
                    for (RegionModel city : citys)
                    {
                        ArrayList<RegionModel> listQu = new ArrayList<>();
                        for (RegionModel original : regionModelArrayList)
                        {
                            if (city.getId() == original.getPid())
                            {
                                listQu.add(original);
                            }
                        }
                        listQuIndex.add(listQu);
                    }
                    mListCounty.add(listQuIndex);
                }
            }
        }



    }

    /**
     * 遍历获取集合内省份的position
     *
     * @return
     */
    private int getProvincePosition() {
        if (TextUtils.isEmpty(province)) {
            return 0;
        } else {
            for (RegionModel model : mListProvince) {
                if (TextUtils.equals(province, model.getName())) {
                    return mListProvince.indexOf(model);
                }
            }
            return 0;
        }
    }

    /**
     * 遍历获取集合内省份对应集合的城市的position
     *
     * @param province_position 省份所在集合对应的position
     * @return
     */
    private int getCityPosition(int province_position) {
        if (TextUtils.isEmpty(city)) {
            return 0;
        } else {
            for (RegionModel model : mListCity.get(province_position)) {
                if (TextUtils.equals(city, model.getName())) {
                    return mListCity.get(province_position).indexOf(model);
                }
            }
            return 0;
        }
    }

    /**
     * 遍历获取集合内省份对应集合的城市的position
     *
     * @param province_position 省份所在集合对应的position
     * @return
     */
    private int getCountyPosition(int province_position, int city_position) {
        if (TextUtils.isEmpty(county)) {
            return 0;
        } else {
            for (RegionModel model : mListCounty.get(province_position).get(city_position)) {
                if (TextUtils.equals(county, model.getName())) {
                    return mListCounty.get(province_position).get(city_position).indexOf(model);
                }
            }
            return 0;
        }
    }


}
