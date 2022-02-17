public class Animal {
    private String voice;
    private int positionX;
    private int positionY;

    public void setVoice(String newVoice){
        voice = newVoice;
    }

    public void makeSound(){
        System.out.println("My voice is " + voice);
        System.out.println("My position is at x= "+positionX+" y= "+positionY);
    }

    public void move(){
        positionX += 5;
    }

}
