package com.example.junhong.tabview2;

/**
 * Created by Junhong on 2015-12-29.
 */
public class ReservedPlan {
    private Reserved[] home_medical;
    private Reserved[] internal;
    private Reserved[] gastrointestinal;
    private Reserved[] stress;
    private Reserved[] neurology;
    private Reserved[] eye;
    private Reserved[] image;
    private Reserved[] ear_nose;
    private Reserved[] dental;
    private Reserved[] skin;

    public ReservedPlan(){
        initialize_home();
        initialize_dental();
        initialize_ear_nose();
        initialize_eye();
        initialize_gas();
        initialize_image();
        initialize_internal();
        initialize_neurology();
        initialize_skin();
        initialize_stress();
    }

    public void initialize_home(){
        home_medical = new Reserved[5];
        home_medical[0].setMorning(false);
        home_medical[0].setAfternoon(true);

        home_medical[1].setMorning(true);
        home_medical[1].setAfternoon(true);

        home_medical[2].setMorning(false);
        home_medical[2].setAfternoon(true);

        home_medical[3].setMorning(true);
        home_medical[3].setAfternoon(true);

        home_medical[4].setMorning(true);
        home_medical[4].setAfternoon(true);
    }

    public void initialize_internal(){
        internal = new Reserved[5];
        internal[0].setMorning(true);
        internal[0].setAfternoon(true);

        internal[1].setMorning(true);
        internal[1].setAfternoon(false);

        internal[2].setMorning(true);
        internal[2].setAfternoon(true);

        internal[3].setMorning(true);
        internal[3].setAfternoon(true);

        internal[4].setMorning(false);
        internal[4].setAfternoon(true);
    }

    public void initialize_gas(){
        gastrointestinal = new Reserved[5];
        gastrointestinal[0].setMorning(true);
        gastrointestinal[0].setAfternoon(true);

        gastrointestinal[1].setMorning(true);
        gastrointestinal[1].setAfternoon(true);

        gastrointestinal[2].setMorning(true);
        gastrointestinal[2].setAfternoon(false);

        gastrointestinal[3].setMorning(true);
        gastrointestinal[3].setAfternoon(true);

        gastrointestinal[4].setMorning(true);
        gastrointestinal[4].setAfternoon(false);
    }

    public void initialize_stress(){
        stress = new Reserved[5];
        stress[0].setMorning(true);
        stress[0].setAfternoon(true);

        stress[1].setMorning(true);
        stress[1].setAfternoon(true);

        stress[2].setMorning(true);
        stress[2].setAfternoon(true);

        stress[3].setMorning(true);
        stress[3].setAfternoon(true);

        stress[4].setMorning(true);
        stress[4].setAfternoon(true);
    }

    public void initialize_neurology(){
        neurology = new Reserved[5];
        neurology[0].setMorning(false);
        neurology[0].setAfternoon(false);

        neurology[1].setMorning(false);
        neurology[1].setAfternoon(false);

        neurology[2].setMorning(false);
        neurology[2].setAfternoon(false);

        neurology[3].setMorning(false);
        neurology[3].setAfternoon(false);

        neurology[4].setMorning(true);
        neurology[4].setAfternoon(false);
    }

    public void initialize_eye(){
        eye = new Reserved[5];
        eye[0].setMorning(false);
        eye[0].setAfternoon(false);

        eye[1].setMorning(false);
        eye[1].setAfternoon(false);

        eye[2].setMorning(true);
        eye[2].setAfternoon(false);

        eye[3].setMorning(false);
        eye[3].setAfternoon(false);

        eye[4].setMorning(true);
        eye[4].setAfternoon(false);
    }

    public void initialize_image(){
        image = new Reserved[5];
        image[0].setMorning(false);
        image[0].setAfternoon(false);

        image[1].setMorning(true);
        image[1].setAfternoon(true);

        image[2].setMorning(false);
        image[2].setAfternoon(false);

        image[3].setMorning(true);
        image[3].setAfternoon(true);

        image[4].setMorning(false);
        image[4].setAfternoon(false);
    }

    public void initialize_ear_nose(){
        ear_nose = new Reserved[5];
        ear_nose[0].setMorning(true);
        ear_nose[0].setAfternoon(true);

        ear_nose[1].setMorning(false);
        ear_nose[1].setAfternoon(false);

        ear_nose[2].setMorning(true);
        ear_nose[2].setAfternoon(true);

        ear_nose[3].setMorning(false);
        ear_nose[3].setAfternoon(false);

        ear_nose[4].setMorning(false);
        ear_nose[4].setAfternoon(false);
    }

    public void initialize_dental(){
        dental = new Reserved[5];
        dental[0].setMorning(true);
        dental[0].setAfternoon(true);

        dental[1].setMorning(true);
        dental[1].setAfternoon(true);

        dental[2].setMorning(true);
        dental[2].setAfternoon(true);

        dental[3].setMorning(true);
        dental[3].setAfternoon(true);

        dental[4].setMorning(true);
        dental[4].setAfternoon(true);
    }

    public void initialize_skin(){
        skin = new Reserved[5];
        skin[0].setMorning(true);
        skin[0].setAfternoon(true);

        skin[1].setMorning(true);
        skin[1].setAfternoon(true);

        skin[2].setMorning(false);
        skin[2].setAfternoon(false);

        skin[3].setMorning(true);
        skin[3].setAfternoon(true);

        skin[4].setMorning(true);
        skin[4].setAfternoon(true);
    }

    class Reserved{
        private boolean morning;
        private boolean afternoon;

        public Reserved(){
            morning = false;
            afternoon = false;
        }

        public void setMorning(boolean value){
            morning = value;
        }

        public void setAfternoon(boolean value){
            afternoon = value;
        }
    }
}
