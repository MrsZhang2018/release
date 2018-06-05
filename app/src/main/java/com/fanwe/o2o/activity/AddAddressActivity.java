package com.fanwe.o2o.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.model.SDTaskRunnable;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDSlidingButton;
import com.fanwe.o2o.R;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dao.CityListModelDao;
import com.fanwe.o2o.event.ERefreshAddressDialog;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppGroupPurListIndexActModel;
import com.fanwe.o2o.model.CityListModel;
import com.fanwe.o2o.model.RegionModel;
import com.fanwe.o2o.model.ShippingAddressModel;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import static com.fanwe.o2o.work.AppRuntimeWorker.HASCITYLIST;

/**
 * 添加地址
 * Created by luod on 2017/3/9.
 */

public class AddAddressActivity extends BaseActivity
{

    @ViewInject(R.id.et_address_name)
    private EditText name;
    @ViewInject(R.id.dialog_address_back)
    private Button back;
    @ViewInject(R.id.dialog_address_complete)
    private Button complete;

    @ViewInject(R.id.et_address_phone)
    private EditText phone;

    @ViewInject(R.id.et_address_address)
    private EditText address;

    @ViewInject(R.id.et_address_doorplate)
    private EditText doorplate;

    @ViewInject(R.id.dialog_address_default)
    private SDSlidingButton is_default;

    @ViewInject(R.id.dialog_address_tv)
    private TextView street;

    @ViewInject(R.id.act_add_delivery_address_tv_delivery)
    private EditText act_add_delivery_address_tv_delivery;

    @ViewInject(R.id.dialog_address_map)
    private RelativeLayout dialog_address_map;

    @ViewInject(R.id.dialog_address_tv)
    private TextView dialog_address_tv;

    @ViewInject(R.id.tv_address_contacts)
    private TextView tv_address_cell;

    @ViewInject(R.id.act_add_address_title)
    private TextView tv_address_title;

    private OptionsPickerView mPickerCity;//城市选择器
    private ArrayList<RegionModel> mListProvince;//省份集合
    private ArrayList<ArrayList<RegionModel>> mListCity;//城市集合
    private ArrayList<ArrayList<ArrayList<RegionModel>>> mListCounty;//区集合

    private String county;
    private String city;//进入城市编辑页面传递的城市
    private String province;//进入省份编辑页面传递的省份

    private boolean mloadSuccess = false;

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
    public static final String EXTRA_MODEL = "extra_model";//区对应的key
    private ShippingAddressModel model;
    private String homeTown;

    private String api_address;
    private double xpoint; // 经度
    private double ypoint; // 纬度

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.dialog_add_address);
        initCityPicker();
        getIntentData();
        initData();
    }

    private void getIntentData()
    {
        mModel = (ShippingAddressModel) getIntent().getSerializableExtra(EXTRA_MODEL);
    }

    private void initData()
    {
        act_add_delivery_address_tv_delivery.setOnClickListener(this);
        back.setOnClickListener(this);
        tv_address_title.setText("添加地址");
        complete.setOnClickListener(this);
        is_default.setOnClickListener(this);
        dialog_address_map.setOnClickListener(this);
        tv_address_cell.setOnClickListener(this);
        if (mModel != null)
        {
            tv_address_title.setText("修改地址");
            name.setText(mModel.getConsignee());
            phone.setText(mModel.getMobile());
            street.setText(mModel.getStreet());
            address.setText(mModel.getAddress());
            doorplate.setText(mModel.getDoorplate());
            region_lv2 = mModel.getRegion_lv2();
            region_lv3 = mModel.getRegion_lv3();
            region_lv4 = mModel.getRegion_lv4();
            String modifyText = getHomeTown(mModel.getRegion_lv2_name(), mModel.getRegion_lv3_name(), mModel.getRegion_lv3_name());
            setText(FLAG_HOMETOWN, modifyText);
            if (mModel.getIs_default() == 0)
            {
                is_default.setSelected(false);
            } else if (mModel.getIs_default() == 1)
            {
                is_default.setSelected(true);
            }
            is_default.setSelectedChangeListener(new SDSlidingButton.SelectedChangeListener()
            {
                @Override
                public void onSelectedChange(SDSlidingButton view, boolean selected)
                {
                    if (mModel.getIs_default() == 1)
                    {
                        if (!selected)
                        {
                            is_default.setSelected(true);
                            b = true;
                            if (first)
                            {
                                first = false;
                                return;
                            } else
                            {
                                Toast.makeText(AddAddressActivity.this, "无法取消默认", Toast.LENGTH_LONG).show();
                            }
                        } else
                        {
                            b = !b;
                        }
                    } else
                    {
                        b = !b;
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.dialog_address_back:
                finish();
                break;
            case R.id.dialog_address_complete:
                if (TextUtils.isEmpty(name.getText()))
                {
                    Toast.makeText(AddAddressActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone.getText()))
                {
                    Toast.makeText(AddAddressActivity.this, "电话不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(street.getText()))
                {
                    Toast.makeText(AddAddressActivity.this, "街道信息不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address.getText()))
                {
                    Toast.makeText(AddAddressActivity.this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
                } else
                {
                    if (mModel != null)
                    {
                        if (b)
                        {
                            mModel.setIs_default(1);
                        } else
                        {
                            mModel.setIs_default(0);
                        }
                        CommonInterface.requestEditShippingAddressListIndex(address.getText().toString(), mModel.getId(), mModel.getIs_default(), name.getText().toString(),
                                phone.getText().toString(), street.getText().toString(), doorplate.getText().toString(), 1, region_lv2,
                                region_lv3, region_lv4, xpoint, ypoint, new AppRequestCallback<AppGroupPurListIndexActModel>()
                                {
                                    @Override
                                    protected void onSuccess(SDResponse sdResponse)
                                    {
                                        ERefreshAddressDialog event = new ERefreshAddressDialog();
                                        SDEventManager.post(event);
                                        Toast.makeText(AddAddressActivity.this, "编辑保存成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                    } else
                    {
                        int is_default;
                        if (b)
                        {
                            is_default = 1;
                        } else
                        {
                            is_default = 0;
                        }
                        CommonInterface.requestAddShippingAddressListIndex(
                                address.getText().toString(),
                                is_default,
                                name.getText().toString(),
                                phone.getText().toString(),
                                street.getText().toString(),
                                doorplate.getText().toString(),
                                1, region_lv2,
                                region_lv3, region_lv4,
                                xpoint, ypoint, new AppRequestCallback<AppGroupPurListIndexActModel>()
                                {
                                    @Override
                                    protected void onSuccess(SDResponse sdResponse)
                                    {
                                        if(actModel.isOk())
                                        {
                                            ERefreshAddressDialog event = new ERefreshAddressDialog();
                                            SDEventManager.post(event);
                                            Toast.makeText(AddAddressActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });
                    }

                }
                break;
            case R.id.dialog_address_default:
                if (mModel != null)
                {
                    if (mModel.getIs_default() == 0)
                    {
                        CommonInterface.requestDefaultShippingAddressListIndex(mModel.getId(), new AppRequestCallback<AppGroupPurListIndexActModel>()
                        {
                            @Override
                            protected void onSuccess(SDResponse sdResponse)
                            {
                                is_default.setSelected(true);
                            }
                        });
                    } else if (mModel.getIs_default() == 1)
                    {
                        Toast.makeText(AddAddressActivity.this, "无法取消默认", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else
                {
                    if (b)
                    {
                        CommonInterface.requestDefaultShippingAddressListIndex(mModel.getId(), new AppRequestCallback<AppGroupPurListIndexActModel>()
                        {
                            @Override
                            protected void onSuccess(SDResponse sdResponse)
                            {
                                is_default.setSelected(true);
                                b = true;
                            }
                        });
                    } else
                    {
                        b = false;
                        return;
                    }
                }
                break;
            case R.id.act_add_delivery_address_tv_delivery:
                //禁用软键盘
                act_add_delivery_address_tv_delivery.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(act_add_delivery_address_tv_delivery.getWindowToken(), 0);
                if (mPickerCity == null)
                {
                    initCityPicker();
                } else
                {
                    showPicker(true);
                }
                break;
            case R.id.dialog_address_map:
                Editable e = act_add_delivery_address_tv_delivery.getText();
                if (TextUtils.isEmpty(e)){
                    SDToast.showToast("请先选择省市区信息");
                }else
                {
                    Intent intent = new Intent(AddAddressActivity.this, AddAddressMapActivity.class);
                    startActivityForResult(intent, 1);
                }

                break;
            case R.id.tv_address_contacts:

                Intent intent_phone = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent_phone, 5);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == 5 && data != null)
            {
                Uri contactData = data.getData();
                ContentResolver contentResolver = getContentResolver();//获取 ContentResolver对象查询在ContentProvider里定义的共享对象；
                Cursor cursor = contentResolver.query(contactData, null, null, null, null);//根据URI对象ContactsContract.Contacts.CONTENT_URI查询所有联系人；
                if (cursor.moveToFirst())  //实际只返回了一行故  cursor.moveToNext()为false
                {
                    String phonenum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phone.setText(phonenum);
                }
                cursor.close();
                return;
            }
            setIntent(data);
            requestIntent();
        }
    }

    private void requestIntent()
    {
        api_address = getIntent().getStringExtra(AddAddressMapActivity.EXTRA_NAME);
        xpoint = getIntent().getDoubleExtra(AddAddressMapActivity.EXTRA_LONGTITUDE, 0);
        ypoint = getIntent().getDoubleExtra(AddAddressMapActivity.EXTRA_LATITUDE, 0);
        address.setText(getIntent().getStringExtra(AddAddressMapActivity.EXTRA_ADDRESS));
        dialog_address_tv.setText(api_address);
    }


    /**
     * 初始化城市选择器
     */
    private int region_lv2;
    private int region_lv3;
    private int region_lv4;

    private void initCityPicker()
    {
        mPickerCity = new OptionsPickerView(AddAddressActivity.this);
        mListProvince = new ArrayList<>();
        mListCity = new ArrayList<>();
        mListCounty = new ArrayList<>();
        mPickerCity.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener()
        {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3)
            {
                province = mListProvince.get(options1).getName();
                city = mListCity.get(options1).get(option2).getName();
                if (mListCounty.get(options1).get(option2).size() > 0)
                {
                    county = mListCounty.get(options1).get(option2).get(options3).getName();
                } else
                {
                    county = "";
                }

                region_lv2 = mListProvince.get(options1).getId();
                region_lv3 = mListCity.get(options1).get(option2).getId();
                if (mListCounty.get(options1).get(option2).size() > 0)
                {
                    region_lv4 = mListCounty.get(options1).get(option2).get(options3).getId();
                } else
                {
                    region_lv4 = 0;
                }
                String modifyText = getHomeTown(province, city, county);

                setText(FLAG_HOMETOWN, modifyText);
            }
        });
        mPickerCity.setCancelable(true);
        showPicker(false);
    }


    /**
     * 获取flag对应的在容器Map中的key
     *
     * @param flag
     * @return
     */
    private String getKey(int flag)
    {
        switch (flag)
        {
            case FLAG_CITY:
                return KEY_CITY;
            case FLAG_PROVINCE:
                return KEY_PROVINCE;
            case FLAG_COUNTY:
                return KEY_COUNTY;
            default:
                return "";
        }
    }

    /**
     * 将修改后的文本显示出来
     *
     * @param flag
     * @param modifyText
     */
    private void setText(int flag, String modifyText)
    {
        switch (flag)
        {
            case FLAG_HOMETOWN:
                homeTown = modifyText;
                act_add_delivery_address_tv_delivery.setText(modifyText);
                break;
            default:
                break;
        }
    }


    /**
     * 格式化 家乡
     *
     * @param province
     * @param city
     * @param county
     * @return “省份 城市”
     */
    private String getHomeTown(String province, String city, String county)
    {
        if (TextUtils.isEmpty(province) && TextUtils.isEmpty(city) && TextUtils.isEmpty(county))
        {
            return "-请选择- -请选择- -请选择-";
        }
        return province + " " + city + " " + county;
    }


    private void showPicker(final boolean isFromClick)
    {
        if (!SDConfig.getInstance().getBoolean(HASCITYLIST, false))
        {
            return;
        }

        SDHandlerManager.getBackgroundHandler().post(new SDTaskRunnable<String>()
        {
            @Override
            public String onBackground()
            {
                if (!mloadSuccess)
                {
//                    SDToast.showToast("地区列表加载中", 1500);
                    CityListModel cityListModel = CityListModelDao.query();
                    if (cityListModel != null)
                    {

                        mloadSuccess = true;
                        mListProvince = cityListModel.getProvinces();
                        mListCity = cityListModel.getCities();
                        mListCounty = cityListModel.getCounties();
                        mPickerCity.setPicker(mListProvince, mListCity, mListCounty, true);
                        mPickerCity.setCyclic(false);
                        mPickerCity.setSelectOptions(getProvincePosition(), getCityPosition(getProvincePosition()), getCountyPosition(getProvincePosition(), getCityPosition(getProvincePosition())));//默认选中
                    }
                }
                return null;
            }

            @Override
            public void onMainThread(String result)
            {
                if(isFromClick)
                {
                    mPickerCity.show();
                }
            }
        });
    }


    /**
     * 遍历获取集合内省份的position
     *
     * @return
     */
    private int getProvincePosition()
    {
        if (TextUtils.isEmpty(province))
        {
            return 0;
        } else
        {
            for (RegionModel model : mListProvince)
            {
                if (TextUtils.equals(province, model.getName()))
                {
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
    private int getCityPosition(int province_position)
    {
        if (TextUtils.isEmpty(city))
        {
            return 0;
        } else
        {
            for (RegionModel model : mListCity.get(province_position))
            {
                if (TextUtils.equals(city, model.getName()))
                {
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
    private int getCountyPosition(int province_position, int city_position)
    {
        if (TextUtils.isEmpty(county))
        {
            return 0;
        } else
        {
            for (RegionModel model : mListCounty.get(province_position).get(city_position))
            {
                if (TextUtils.equals(county, model.getName()))
                {
                    return mListCounty.get(province_position).get(city_position).indexOf(model);
                }
            }
            return 0;
        }
    }


}
