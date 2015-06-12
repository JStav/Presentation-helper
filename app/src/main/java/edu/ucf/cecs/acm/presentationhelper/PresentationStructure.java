package edu.ucf.cecs.acm.presentationhelper;

/**
 * Created by kishoredebnath on 01/04/15.
 */
public class PresentationStructure {

    //Static data to all the slide objects
    private static int slideId;
    private static int totalDuration, totalSlides, maxDuration = 2;
    public static PresentationStructure presentation = null;
    public static boolean customTimeShared = false;

    private int sId;
    private int currentDuration;
    public PresentationStructure nextSlide;


    //Constructor per slide - slide builder
    private PresentationStructure(int duration, int slideId){

        this.currentDuration = duration;
        this.sId = slideId;
        this.nextSlide = null;
    }

    //Create Presentation for the given number of slides each with time: duration
    private static void createPresentation(int duration){
        if(PresentationStructure.totalSlides>0) {
            slideId = 1;
            if(presentation == null) {
                presentation = new PresentationStructure(duration, slideId);
            }else {
                presentation.currentDuration = duration;
            }

            PresentationStructure current = presentation;

            for(int i = 1; i< PresentationStructure.totalSlides; i++){
                slideId++;
                if(current.nextSlide == null) {
                    current.nextSlide = new PresentationStructure(duration, slideId);
                }else{
                    current.nextSlide.currentDuration = duration;
                }
                current = current.nextSlide;
            }
            current.nextSlide = null;
            maxDuration = duration;
        }
    }

    //Returns the Singleton head pointer pointing to the slides of the presentations
    public static PresentationStructure getPresentation(int totalDuration, int totalSlides){

        int slideDuration = 0;

        if(presentation == null){

            PresentationStructure.totalDuration = totalDuration;
            PresentationStructure.totalSlides = totalSlides;
            slideDuration = totalDuration/totalSlides;

            createPresentation(slideDuration);

            return presentation;

        }else{
            //return the same presentation object if the duration and number of slides are same
            if(totalDuration == PresentationStructure.totalDuration && totalSlides== PresentationStructure.totalSlides && !PresentationStructure.customTimeShared){


                return presentation;

            }else{

                //update the duration or slide accordingly and return the presentation object

                PresentationStructure.totalDuration = totalDuration;
                PresentationStructure.totalSlides = totalSlides;
                slideDuration = totalDuration/totalSlides;

                PresentationStructure.customTimeShared = false;
                createPresentation(slideDuration);

                return presentation;

            }

        }

    }

    public static String generateStringFromSlides(){

        if(presentation != null){

            PresentationStructure current = presentation;
            String result = "";

            while(current!=null){

                result += current.currentDuration+"/";
                current = current.nextSlide;

            }

            result += "eof";

            return result;

        }

        return null;

    }

    public int slideId(){
        return this.sId;
    }

    public int getDuration(){
        return this.currentDuration;
    }

    public void setDuration(int duration){

        if(!PresentationStructure.customTimeShared) {
            PresentationStructure.customTimeShared = true;
        }

        PresentationStructure.totalDuration += duration-this.currentDuration;

        if(duration-this.currentDuration>0&& PresentationStructure.maxDuration>duration){
            PresentationStructure.maxDuration = duration;
        }

        this.currentDuration = duration;

    }

    public static int getTotalDuration(){return PresentationStructure.totalDuration;}

    public static int getTotalSlides(){return PresentationStructure.totalSlides;}

    public static int getMaxDuration(){return PresentationStructure.maxDuration;}

}
