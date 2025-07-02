package src;

import java.io.*;
import java.util.*;

public class GPSLog {
    public List<GPSPoint> points = new ArrayList<>();
    private String filename;

    public GPSLog(String filename) { this.filename = filename; }

    public void parse() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();

        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split(",");

            if (parts.length < 5) continue;

            double lat = Double.parseDouble(parts[0]);
            double lon = Double.parseDouble(parts[1]);
            double angle = Double.parseDouble(parts[2]);
            double speed = Double.parseDouble(parts[3]);
            double hdop = Double.parseDouble(parts[4]);
            
            points.add(new GPSPoint(lat, lon, angle, speed, hdop));
        }
        br.close();
    }
}
