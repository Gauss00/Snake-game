package Snake;

import java.io.IOException;
import java.awt.event.KeyEvent;

public class Room {

    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;
    public static Room game;

    public static void main(String[] args){
        game = new Room(20, 20, new Snake(10, 10));
        game.snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();
    }

    public void run (){
        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //пока змея жива
        while (snake.isAlive()) {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если равно символу 'q' - выйти из игры.
                if (event.getKeyChar() == 'q') return;

                //Если "стрелка влево" - сдвинуть фигурку влево
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                    //Если "стрелка вправо" - сдвинуть фигурку вправо
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);
                    //Если "стрелка вверх" - сдвинуть фигурку вверх
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                    //Если "стрелка вниз" - сдвинуть фигурку вниз
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }

            snake.move();   //двигаем змею
            print();        //отображаем текущее состояние игры
            sleep();        //пауза между ходами
        }

        //Выводим сообщение "Game Over"
        System.out.println("Game Over!");
    }
    public void print(){
        int matrix[][] = new int[height][width];
        matrix[snake.getY()][snake.getX()] = 2;
        for (int i = 1; i < snake.getSections().size(); i++) {
            matrix[snake.getSections().get(i).getY()][snake.getSections().get(i).getX()] = 1;
        }
        matrix[mouse.getY()][mouse.getX()] = 3;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(matrix[i][j] == 1){
                    System.out.print("x");
                }else if(matrix[i][j] == 2){
                    System.out.print("X");
                }else if(matrix[i][j] == 3){
                    System.out.print("^");
                }else{
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    public void createMouse(){
        mouse = new Mouse((int) (Math.random() * width), (int) (Math.random() * height));
    }

    public void eatMouse(){
        createMouse();
    }

    public void sleep(){
        try {
            if (snake.getSections().size() <= 15) {
                Thread.sleep(520-(snake.getSections().size()*20));
            }else{
                Thread.sleep(200);
            }

        }catch (InterruptedException e){}
    }

    public Room(int width, int height, Snake snake) {
        this.width = width; this.height = height; this.snake = snake;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    public Mouse getMouse() {
        return mouse;
    }

    public Snake getSnake() {
        return snake;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
