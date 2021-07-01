package com.chzu.util;

import com.chzu.enums.FileType;
import com.chzu.model.TbFile;
import com.chzu.model.TbTarget;
import com.chzu.model.bo.GradeInfo;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TargetCalculationUtil {
    public static int[][] grade = new int[10][6];
    public static int[][] proportion = new int[10][6];
    private static BigDecimal[] passmark = new BigDecimal[10];

    static {
        for (int i=0;i<passmark.length;i++) {
            passmark[i] = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP);
        }
    }

    /**
     * 由数据库中的信息得到各课程目标下课程实践、团队合作、考核答辩、系统实现和实践报告五部分的分数和占比
     * @param targetList
     */
    public static void getRawData(List<TbTarget> targetList) {
        String tg = targetList.get(0).getTarget();
        int index = 0;
        //标记，标记是否为有效数据
        grade[index][5] = 1;
        proportion[index][5] = 1;
        for (TbTarget target : targetList) {
            if (!tg.equals(target.getTarget())) {
                index++;
                //设置此条数据为有效数据
                grade[index][5] = 1;
                proportion[index][5] = 1;

                tg = target.getTarget();
            }
            grade[index][target.getItem()] = target.getGrade();
            proportion[index][target.getItem()] = target.getProportion();
        }
    }

    /**
     * 得到课程目标grade分数的有效长度
     * @return
     */
    private static int getEffectiveLengthFromGrade() {
        for (int i=0;i<grade.length;i++) {
            if (grade[i][5] == 0)
                return i;
        }
        return 0;
    }

    /**
     * 计算合格分
     */
    private static void  calculatePassmark() {
        for (int i=0;i<grade.length;i++) {
            //无效课程目标的数据，停止循环
            if (grade[i][5] == 0) {
                break;
            }
            BigDecimal source = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP);
            for (int j=0;j<5;j++) {
                if (grade[i][j] == 0) continue;
                source = source.add( new BigDecimal ( (grade[i][j] * proportion[i][j] / 100.00 ) * 0.6)
                        .setScale(2, RoundingMode.HALF_UP));
            }
            System.out.println("合格成绩： " + source.toString());
            passmark[i] = source;
        }
    }

    /**
     * 计算个人的各项目标达成度
     * @param personalGrade
     * @return
     */
    public static BigDecimal[] calculatePersonalGoal(GradeInfo personalGrade) {
        BigDecimal[] result = new BigDecimal[10];
        int[] list = {personalGrade.getGrade1(),personalGrade.getGrade2(),
        personalGrade.getGrade3(),personalGrade.getGrade4(),personalGrade.getGrade5()};

        for (int i=0;i<grade.length;i++) {
            //无效课程目标的数据，停止循环
            if (grade[i][5] == 0) {
                break;
            }
            BigDecimal source = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP);
            for (int j=0;j<5;j++) {
                if (grade[i][j] == 0) continue;
                source = source.add( new BigDecimal (list[j] * grade[i][j] * proportion[i][j] /10000.00)
                        .setScale(2, RoundingMode.HALF_UP));

            }
            result[i] = source;
        }
        return result;
    }

    // 计算平均分
    private static List<BigDecimal[]> getData(List<GradeInfo> gradeInfoList) {
        List<BigDecimal[]> list = new ArrayList<>();
        //存储平均分
        BigDecimal[] avgs = new BigDecimal[10];
        //各项初始化
        for (int j=0; j < getEffectiveLengthFromGrade() ; j ++) {
            avgs[j] = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP);
        }

        for (int i=0;i<gradeInfoList.size();i++) {
            //得到个人成绩及相关信息
            GradeInfo personalGrade = gradeInfoList.get(i);
            //得到个人各项目标达成度
            BigDecimal[] achievement = calculatePersonalGoal(personalGrade);

            for (int j=0; j < getEffectiveLengthFromGrade() ; j ++) {
                avgs[j] = avgs[j].add(achievement[j]);
            }

            list.add(achievement);
        }

        for (int j=0; j < getEffectiveLengthFromGrade() ; j ++) {
            avgs[j] = avgs[j].divide(new BigDecimal(gradeInfoList.size()),2, BigDecimal.ROUND_HALF_DOWN);
        }
        list.add(avgs);
        return list;
    }
    
    public static TbFile calculateGrade(List<GradeInfo> gradeInfoList) {
        TbFile achievementTable = new TbFile();
        achievementTable.setId(SmallUtil.getGeneratID());
        String fileName = "目标达成度表格-" + SmallUtil.getDate("yyyy-MM-dd-HH-mm-ss") + ".xlsx" ;
        achievementTable.setFileName(fileName);

        //表头
        String[] th = {"学号","姓名","成绩","平均成绩","合格成绩"};

        //得到成绩数据
        List<BigDecimal[]> data = getData(gradeInfoList);
        //得到平均分
        BigDecimal[] avgs = data.get(data.size()-1);
        //计算合格成绩
        calculatePassmark();

        XSSFWorkbook workbook = new XSSFWorkbook();
        for (int i=0;i<gradeInfoList.size();i++) {
            //得到个人成绩及相关信息
            GradeInfo personalGrade = gradeInfoList.get(i);
            //得到个人各项目标达成度
            BigDecimal[] achievement = data.get(i);


            for (int j=0; j < getEffectiveLengthFromGrade() ; j ++) {

                XSSFSheet sheet = null;
                //第一次循环
                if (i == 0) {
                    //创建表单
                    sheet = workbook.createSheet("课程目标" + (j + 1));

                    //创建第一行
                    XSSFRow row = sheet.createRow(0);
                    //创建列
                    row.createCell(1).setCellValue(th[0]);
                    row.createCell(2).setCellValue(th[1]);
                    row.createCell(3).setCellValue(th[2]);
                    row.createCell(4).setCellValue(th[3]);
                    row.createCell(5).setCellValue(th[4]);
                }
                //非第一次循环，得到表单
                if (i > 0)
                    sheet = workbook.getSheet("课程目标" + (j + 1));

                XSSFRow row = sheet.createRow(i + 1);
                //填写信息，达成度分数
                row.createCell(0).setCellValue(i+1);
                row.createCell(1).setCellValue(personalGrade.getNumber());
                row.createCell(2).setCellValue(personalGrade.getName());
                row.createCell(3).setCellValue(achievement[j].toString());
                row.createCell(4).setCellValue(avgs[j].toString());
                row.createCell(5).setCellValue(passmark[j].toString());
            }
        }
        //文件存储位置
        String path = "E:\\design-manager-system\\uploads\\achievementTable\\" + fileName + ".xlsx";
        achievementTable.setFilePath(path);

        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //将生成的Excel文件保存到本地
        try (FileOutputStream out = new FileOutputStream(file) ) {
            //将工作薄写入文件输出流中
            workbook.write(out);
        }catch (Exception e) {
            e.printStackTrace();
        }

        achievementTable.setUpId("system");
        achievementTable.setFileType(FileType.achievementTable.type);
        achievementTable.setUpTime(new Date());

        return achievementTable;
    }

}
