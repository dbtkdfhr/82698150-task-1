package src;

import java.io.*;
import java.util.*;

//gps 파일을 파싱하기 위한 클래스
public class GPSLog {
    public List<GPSPoint> points = new ArrayList<>();
    private String filename;

    public GPSLog(String filename) { this.filename = filename; }

    public void parse() throws Exception { 
        //파일 데이터 읽기
        BufferedReader br = new BufferedReader(new FileReader(filename));

        //첫 줄은 넘기기
        String line = br.readLine();

        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split(",");

            if (parts.length < 5) continue;

            double lat = Double.parseDouble(parts[0]);
            double lon = Double.parseDouble(parts[1]);
            double angle = Double.parseDouble(parts[2]);
            double speed = Double.parseDouble(parts[3]);
            double hdop = Double.parseDouble(parts[4]);
            
            //한 줄씩 GPSPoint 클래스로 해석하여 저장
            points.add(new GPSPoint(lat, lon, angle, speed, hdop));
        }
        br.close();
    }
}
