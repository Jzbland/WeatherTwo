package com.beestar.jzb.mylineview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LineChart lineChart;
    protected String[] values = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };
    private ArrayList<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        settingline();

    }


    private void initView() {
        lineChart = (LineChart) findViewById(R.id.testchart);
    }

    private void settingline() {
        Description description = new Description();
        description.setText("这是一个折线图");
        description.setTextColor(Color.RED);
        description.setTextSize(20);
        lineChart.setDescription(description);//设置图表描述信息
        lineChart.setNoDataText("没有数据熬");//没有数据时显示的文字
        lineChart.setNoDataTextColor(Color.BLUE);//没有数据时显示文字的颜色
        lineChart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
        lineChart.setDrawBorders(false);//禁止绘制图表边框的线


        /**
         * Entry 坐标点对象  构造函数 第一个参数为x点坐标 第二个为y点
         */
        ArrayList<Entry> values1 = new ArrayList<>();

        values1.add(new Entry(4, 10));
        values1.add(new Entry(6, 15));
        values1.add(new Entry(9, 20));
        values1.add(new Entry(12, 5));
        values1.add(new Entry(15, 30));


        //LineDataSet每一个对象就是一条连接线
        LineDataSet set1;
        LineDataSet set2;

        //判断图表中原来是否有数据
        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            //获取数据1
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);

            //刷新数据
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            //设置数据1  参数1：数据源 参数2：图例名称
            set1 = new LineDataSet(values1, "测试数据1");
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);//设置线宽
            set1.setCircleRadius(3f);//设置焦点圆心的大小
            set1.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
            set1.setHighlightLineWidth(2f);//设置点击交点后显示高亮线宽
            set1.setHighlightEnabled(true);//是否禁用点击高亮线
            set1.setHighLightColor(Color.RED);//设置点击交点后显示交高亮线的颜色
            set1.setValueTextSize(9f);//设置显示值的文字大小
            set1.setDrawFilled(false);//设置禁用范围背景填充

            //格式化显示数据
            final DecimalFormat mFormat = new DecimalFormat("###,###,##0");
            set1.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return mFormat.format(value);
                }
            });


            //保存LineDataSet集合
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the datasets
            //创建LineData对象 属于LineChart折线图的数据集合
            LineData data = new LineData(dataSets);
            // 添加到图表中
            lineChart.setData(data);
            //绘制图表
            lineChart.invalidate();


            //获取此图表的x轴
            XAxis xAxis = lineChart.getXAxis();
            xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
            xAxis.setDrawAxisLine(true);//是否绘制轴线
            xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
            xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
            //xAxis.setTextSize(20f);//设置字体
            //xAxis.setTextColor(Color.BLACK);//设置字体颜色
            //设置竖线的显示样式为虚线
            //lineLength控制虚线段的长度
            //spaceLength控制线之间的空间
            xAxis.enableGridDashedLine(10f, 10f, 0f);
//        xAxis.setAxisMinimum(0f);//设置x轴的最小值
//        xAxis.setAxisMaximum(10f);//设置最大值
            xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
            xAxis.setLabelRotationAngle(10f);//设置x轴标签的旋转角度
//        设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
//        xAxis.setLabelCount(10);
//        xAxis.setTextColor(Color.BLUE);//设置轴标签的颜色
//        xAxis.setTextSize(24f);//设置轴标签的大小
//        xAxis.setGridLineWidth(10f);//设置竖线大小
//        xAxis.setGridColor(Color.RED);//设置竖线颜色
//        xAxis.setAxisLineColor(Color.GREEN);//设置x轴线颜色
//        xAxis.setAxisLineWidth(5f);//设置x轴线宽度
//        xAxis.setValueFormatter();//格式化x轴标签显示字符


            /**
             * Y轴默认显示左右两个轴线
             */
            //获取右边的轴线
            YAxis rightAxis = lineChart.getAxisRight();
            //设置图表右边的y轴禁用
            rightAxis.setEnabled(false);
            //获取左边的轴线
            YAxis leftAxis = lineChart.getAxisLeft();
            //设置网格线为虚线效果
            leftAxis.enableGridDashedLine(10f, 10f, 0f);
            //是否绘制0所在的网格线
            leftAxis.setDrawZeroLine(false);
        }

//    private void setChart2() {
//        lineChart.getDescription().setEnabled(false);
//
//        //自定义x轴显示
//
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return String.valueOf(data.get((int) value));
//            }
//        });
//        xAxis.setDrawAxisLine(false);
//        xAxis.setDrawGridLines(false);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        //显示个数
//        xAxis.setLabelCount(12);
//
//
//        //1 创建类型的列表Entry ，将保留您的值：
//        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
//
//        //2) 然后，给 lists 集合添加 Entry 对象。
//        //确保 Entry 对象包含的 index 都是正确的 （对于x轴来说）。
//        for (int i = 0; i < 12; i++) {
//            valsComp1.add(new Entry(i, (float) (50.000f + Math.random() * 20)));
//        }
//        //3 有了 Entry 对象的 lists 集合，再创建 LineDataSet 对象：
//        LineDataSet setComp1 = new LineDataSet(valsComp1, "");
//        //4  DataSets 集合和 x-axis entries 集合，来创建我们的 ChartData 对象：
//        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//        dataSets.add(setComp1);
//        LineData data = new LineData(dataSets);
//        data.setDrawValues(true);
//        lineChart.setData(data);
//        MyMakerView mv=new MyMakerView(this,R.layout.layout_marker);
//        lineChart.setMarker(mv);
//        lineChart.invalidate();
//
//    }
//

//
//    private void setChart() {
//        float[] dataObjects = {1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1};
//        List<Entry> entries = new ArrayList<>();
//        for (int i = 0; i < dataObjects.length; i++) {
//            float data = dataObjects[i];
//            entries.add(new Entry(i, data));
//        }
//        LineDataSet dataSet = new LineDataSet(entries, "Label1");
//        dataSet.setColors(Color.BLACK, Color.GRAY, Color.RED, Color.GREEN); // 每个点之间线的颜色，还有其他几个方法，自己看
//        dataSet.setValueFormatter(new IValueFormatter() {   // 将值转换为想要显示的形式，比如，某点值为1，变为“1￥”,MP提供了三个默认的转换器，
//            // LargeValueFormatter:将大数字变为带单位数字；PercentFormatter：将值转换为百分数；StackedValueFormatter，对于BarChart，是否只显示最大值图还是都显示
//            @Override
//            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//                return value + "￥";
//            }
//        });
//        LineData lineData = new LineData(dataSet);
//        lineChart.setData(lineData);
//
//
//        lineChart.setBackgroundColor(Color.WHITE); // 整个图标的背景色
//        Description description = new Description();  // 这部分是深度定制描述文本，颜色，字体等
//        description.setText("24小时温度表");
//        description.setTextColor(Color.RED);
//        lineChart.setDescription(description);
//        lineChart.setNoDataText("暂无数据");   // 没有数据时样式
//        lineChart.setDrawGridBackground(false);    // 绘制区域的背景（默认是一个灰色矩形背景）将绘制，默认false
//        lineChart.setGridBackgroundColor(Color.WHITE);  // 绘制区域的背景色
//        lineChart.setDrawBorders(false);    // 绘制区域边框绘制，默认false
//        lineChart.setBorderColor(Color.GREEN); // 边框颜色
//        lineChart.setBorderWidth(2);   // 边框宽度,dp
//        lineChart.setMaxVisibleValueCount(14);  // 数据点上显示的标签，最大数量，默认100。且dataSet.setDrawValues(true);必须为true。只有当数据数量小于该值才会绘制标签
//
//
//
////        MyXFormatter formatter = new MyXFormatter(values);
////        XAxis xAxis = lineChart.getXAxis();
////        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
////        xAxis.setDrawAxisLine(false);
////        xAxis.setDrawGridLines(false);
////        //显示个数
////        xAxis.setLabelCount(12);
////        xAxis.setValueFormatter(formatter);
//
//        // *************************轴****************************** //
//        // 由四个元素组成：
//        // 标签：即刻度值。也可以自定义，比如时间，距离等等，下面会说一下；
//        // 轴线：坐标轴；
//        // 网格线：垂直于轴线对应每个值画的轴线；
//        // 限制线：最值等线。
//        XAxis xAxis = lineChart.getXAxis();    // 获取X轴
//        YAxis yAxis = lineChart.getAxisLeft(); // 获取Y轴,mLineChart.getAxis(YAxis.AxisDependency.LEFT);也可以获取Y轴
//        lineChart.getAxisRight().setEnabled(false);    // 不绘制右侧的轴线
//        xAxis.setEnabled(true); // 轴线是否可编辑,默认true
//        xAxis.setDrawLabels(true);  // 是否绘制标签,默认true
//        xAxis.setDrawAxisLine(true);    // 是否绘制坐标轴,默认true
//        xAxis.setDrawGridLines(false);   // 是否绘制网格线，默认true
//        xAxis.setAxisMaximum(10); // 此轴能显示的最大值；
//        xAxis.resetAxisMaximum();   // 撤销最大值；
//        xAxis.setAxisMinimum(1);    // 此轴显示的最小值；
//        xAxis.resetAxisMinimum();   // 撤销最小值；
////        yAxis.setStartAtZero(true);  // 从0开始绘制。已弃用。使用setAxisMinimum(float)；
////        yAxis.setInverted(true); // 反转轴,默认false
//        yAxis.setSpaceTop(10);   // 设置最大值到图标顶部的距离占所有数据范围的比例。默认10，y轴独有
//        // 算法：比例 = （y轴最大值 - 数据最大值）/ (数据最大值 - 数据最小值) ；
//        // 用途：可以通过设置该比例，使线最大最小值不会触碰到图标的边界。
//        // 注意：设置一条线可能不太好看，mLineChart.getAxisRight().setSpaceTop(34)也设置比较好;同时，如果设置最小值，最大值，会影响该效果
//        yAxis.setSpaceBottom(10);   // 同上，只不过是最小值距离底部比例。默认10，y轴独有
//        // yAxis.setShowOnlyMinMax(true);   // 没找到。。。，true, 轴上只显示最大最小标签忽略指定的数量（setLabelCount，如果forced = false).
//        yAxis.setLabelCount(4, false); // 纵轴上标签显示的数量,数字不固定。如果force = true,将会画出明确数量，但是可能值导致不均匀，默认（6，false）
//        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);  // 标签绘制位置。默认再坐标轴外面
//        xAxis.setGranularity(2); // 设置X轴值之间最小距离。正常放大到一定地步，标签变为小数值，到一定地步，相邻标签都是一样的。这里是指定相邻标签间最小差，防止重复。
//        yAxis.setGranularity(1);    // 同上
//        yAxis.setGranularityEnabled(false); // 是否禁用上面颗粒度限制。默认false
//        // 轴颜色
//        yAxis.setTextColor(Color.RED);  // 标签字体颜色
//        yAxis.setTextSize(10);    // 标签字体大小，dp，6-24之间，默认为10dp
//        yAxis.setTypeface(null);    // 标签字体
//        yAxis.setGridColor(Color.GRAY);    // 网格线颜色，默认GRAY
//        yAxis.setGridLineWidth(1);    // 网格线宽度，dp，默认1dp
//        yAxis.setAxisLineColor(Color.RED);  // 坐标轴颜色，默认GRAY.测试到一个bug，假如左侧线只有1dp，
//        // 那么如果x轴有线且有网格线，当刻度拉的正好位置时会覆盖到y轴的轴线，变为X轴网格线颜色，结局办法是，要么不画轴线，要么就是坐标轴稍微宽点
//        xAxis.setAxisLineColor(Color.RED);
//        yAxis.setAxisLineWidth(2);  // 坐标轴线宽度，dp，默认1dp
//        yAxis.enableGridDashedLine(20, 10, 1);    // 网格线为虚线，lineLength，每段实线长度,spaceLength,虚线间隔长度，phase，起始点（进过测试，最后这个参数也没看出来干啥的）
//        // 限制线
//        LimitLine ll = new LimitLine(6.5f, "上限"); // 创建限制线, 这个线还有一些相关的绘制属性方法，自行看一下就行，没多少东西。
//        yAxis.addLimitLine(ll); // 添加限制线到轴上
//        yAxis.removeLimitLine(ll);  // 移除指定的限制线,还有其他的几个移除方法
//        yAxis.setDrawLimitLinesBehindData(false); // 限制线在数据之后绘制。默认为false
//
//        // X轴更多属性
//        xAxis.setLabelRotationAngle(45);   // 标签倾斜
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // X轴绘制位置，默认是顶部
//
//        // Y轴更多属性
//        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);  // 设置dataSet应绘制在Y轴的左轴还是右轴，默认LEFT
//        yAxis.setDrawZeroLine(false);    // 绘制值为0的轴，默认false,其实比较有用的就是在柱形图，当有负数时，显示在0轴以下，其他的图这个可能会看到一些奇葩的效果
//        yAxis.setZeroLineWidth(10);  // 0轴宽度
//        yAxis.setZeroLineColor(Color.BLUE);   // 0轴颜色
//
//        // 轴值转换显示
//        xAxis.setValueFormatter(new IAxisValueFormatter() { // 与上面值转换一样，这里就是转换出轴上label的显示。也有几个默认的，不多说了。
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return value + "";
//            }
//        });
//        lineChart.invalidate();
//    }
    }

}
