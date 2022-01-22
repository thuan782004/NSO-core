package net.bearmine.nso_core.lib.other;

import org.bukkit.configuration.ConfigurationSection;

import java.util.Random;
import java.util.Set;

public class generatingid {
    public static String generateid(ConfigurationSection file,String prefix){

        if (file==null||file.getKeys(false).size()==0)
            return random(prefix);  //nếu không có khóa nào tồn tại thì chọn ngay 1 chuỗi ngẫu nhiên
        Set<String> set = file.getKeys(false);  //lấy danh sách từ khóa
        String newid = random(prefix);   //tạo chuỗi ngẫu nhiên mới
    while (set.contains(newid)) { //nếu chuỗi ngẫu nhiên đã tồn tại
        newid = random(prefix);         //thì tiếp tục chọn chuỗi mới cho đến khi không còn trùng khớp
    }
    return newid;                 //trả về chuỗi đó
}

    private static String random(String prefix){
        StringBuilder rd = new StringBuilder(prefix);  //tạo cơ sở cho chuỗi <prefix>
        for (int i=0;i<8;i++) {             //tiến trình này lặp lại 8 lần cho ra 8 ký tự ngẫu nhiên
            Random random = new Random();           //tạo hàm ngẫu nhiên mới
            int upcase = 48;                        //giá trị mặc định cho giới hạn trên  kí tự 0
            int lowcase = 58;                       //giá trị mặc định cho giới hạn dưới  ký tự 9
            int ranfom = random.nextInt(3);  //Tạo số ngẫu nhiên từ 0-2
            switch (ranfom){
                case 1:                             //nếu là 1
                    upcase = 65;                    //giá trị cho giới hạn trên  kí tự A
                    lowcase = 91;                   //giá trị cho giới hạn trên  kí tự Z
                    break;
                case 2:
                    upcase = 97;                    //giá trị cho giới hạn trên  kí tự a
                    lowcase = 123;                  //giá trị cho giới hạn trên  kí tự z
                    break;
            }
            Random random1 = new Random();      //tạo hàm ngẫu nhiên thứ 2
            rd.append(random1.ints(upcase, lowcase)        //lấy ký tự ngẫu nhiên trong khoảng giới hạn
                    .limit(1)                              //chỉ lấy 1 ký tự
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString());
        }
        return rd.toString();               //xuất ra thành chuỗi
    }
}
