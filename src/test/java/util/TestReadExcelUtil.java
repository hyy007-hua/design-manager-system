package util;

import com.chzu.util.ReadExcelUtil;

public class TestReadExcelUtil {
    public static void main(String[] args) {
        try {
            //ReadExcelUtil.parseTypeFromInputFile("D:\\IDEA-JavaWeb\\design-manager-system\\target\\design-manager-system\\uploads\\excelDAB09544FC324162B09BF8AB405FA754_计科17.xlsx");
            String[][] re = ReadExcelUtil.parseInfoFromInputFile("E:\\design-manager-system\\uploads\\excel\\0CA87E116A824904B8F9138F70B6E610_计科17.xlsx");
            for(int i = 0;i<re.length;++i){
                if(re[i][0] == null)
                    break;
                for(int j=0;j<re[i].length;++j){
                    if(re[i][j] == null)
                        break;
                    System.out.print("  "+re[i][j]);
                }
                System.out.println();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
