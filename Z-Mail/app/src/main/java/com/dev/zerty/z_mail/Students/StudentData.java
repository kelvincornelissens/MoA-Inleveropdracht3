package com.dev.zerty.z_mail.Students;

import com.dev.zerty.z_mail.R;

import java.util.ArrayList;
import java.util.Collections;

public class StudentData {

    //List with info about the students
    public static ArrayList<Student> studentList() {

        ArrayList<Student> students = new ArrayList<>();
        Student TimBruntink = new Student("0267599", "I4AO1", "img0267599", "Tim", "", "Bruntink", "7566 DS", "Hengelo OV", "tbruntink01@student.rocvantwente.nl", 52.272361, 6.788581);
        students.add(TimBruntink);

        Student KelvinCornelissen = new Student("0267772", "I4AO1", "img0267772", "Kelvin", "", "Cornelissen", "7622 KV", "Borne", "kcornelissens01@student.rocvantwente.nl", 52.296514, 6.754556);
        students.add(KelvinCornelissen);

        Student MartijnDekker = new Student("0256907", "I4AO1", "img0256907", "Martijn", "", "Dekker", "7471 ZI", "Goor", "mdekker04@student.rocvantwente.nl", 52.236871, 6.587225);
        students.add(MartijnDekker);

        Student DylanDoornbos = new Student("0265788", "I4AO1", "img0265788", "Dylan", "", "Doornbos", "7545 KH", "Enschede", "ddoornbos01@student.rocvantwente.nl", 52.215072, 6.868041);
        students.add(DylanDoornbos);

        Student BartVanEs = new Student("0264650", "I4AO1", "img0264650", "Bart", "van", "Es", "7534 ME", "Enschede", "bvanes01@student.rocvantwente.nl", 52.210541, 6.962569);
        students.add(BartVanEs);

        Student LoekGosen = new Student("0267853", "I4AO1", "img0267853", "Loek", "", "Gosen", "7574 ZV", "Oldenzaal", "lgosen01@student.rocvantwente.nl", 52.301946, 6.932061);
        students.add(LoekGosen);

        Student BasGrave = new Student("0267617", "I4AO1", "img0267617", "Bas", "", "Grave", "7557 GE", "Hengelo OV", "bgrave01@student.rocvantwente.nl", 52.272653, 6.813392);
        students.add(BasGrave);

        Student DylanHofstra = new Student("0263413", "I4AO1", "img0263413", "Dylan", "", "Hofstra", "7534 JX", "Enschede", "dhofstra02@student.rocvantwente.nl", 52.212315, 6.949346);
        students.add(DylanHofstra);

        Student JordyMengerink = new Student("0265597", "I4AO1", "img0265597", "Jordy", "", "Mengerink", "7559 KT", "Hengelo OV", "jmengerink03@student.rocvantwente.nl", 52.296623, 6.803050);
        students.add(JordyMengerink);

        Student JamesMorsink = new Student("0267422", "I4AO1", "img0267422", "James", "", "Morsink", "7557 JB", "Hengelo OV", "jmorsink09@student.rocvantwente.nl", 52.272109, 6.804139);
        students.add(JamesMorsink);

        Student RobinTatlici = new Student("0187199", "I4AO1", "img0187199", "Robin", "", "Tatlici", "7512 XM", "Enschede", "rtatlici01@student.rocvantwente.nl", 52.215606, 6.904909);
        students.add(RobinTatlici);

        Student LaurensTel = new Student("0179028", "I4AO1", "img0179028", "Laurens", "", "Tel", "7577 MD", "Oldenzaal", "ltel01@student.rocvantwente.nl", 52.319950, 6.913073);
        students.add(LaurensTel);

        Student CarloVerver = new Student("0269264", "I4AO1", "img0269264", "Carlo", "", "Verver", "7205 BH", "Zutphen", "cverver01@student.rocvantwente.nl", 52.142890, 6.182103);
        students.add(CarloVerver);

        Student FirdhanYahya = new Student("267433", "I4AO1", "img267433", "Firdhan", "", "Yahya", "7556 PP", "Hengelo", "fyahya01@student.rocvantwente.nl", 52.277175, 6.793904);
        students.add(FirdhanYahya);

        Student JakeZweers = new Student("0257956", "I4AO1", "img0257956", "Jake", "", "Zweers", "7462 BD", "Rijssen", "jzweers05@student.rocvantwente.nl", 52.300704, 6.520902);
        students.add(JakeZweers);

        return students;
    }
}
