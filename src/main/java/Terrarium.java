public class Terrarium {

    public static void main(String[] args) {
        Animal cat =  new Animal();
        cat.setVoice("mew");
        cat.move();
        cat.makeSound();
        Animal dog = new Animal();
        dog.setVoice("wav");
        dog.makeSound();

        Animal fly = new Animal();
        fly.makeSound();
    }
}
