package Snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public Snake(int x, int y) {
        sections = new ArrayList<SnakeSection>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;

    }

    public int getX(){
        return sections.get(0).getX();
    }
    public int getY(){
        return sections.get(0).getY();
    }
    public void move(int dx, int dy){
        //Создаем новую голову - новый "кусочек змеи".
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX() + dx, head.getY() + dy);

        //Проверяем - не вылезла ли голова за границу комнаты
        checkBorders(head);
        if (!isAlive) return;

        //Проверяем - не пересекает ли змея  саму себя
        checkBody(head);
        if (!isAlive) return;

        //Проверяем - не съела ли змея мышь.
        Mouse mouse = Room.game.getMouse();
        if (head.getX() == mouse.getX() && head.getY() == mouse.getY()) //съела
        {
            sections.add(0, head);                  //Добавили новую голову
            Room.game.eatMouse();                   //Хвост не удаляем, но создаем новую мышь.
        } else //просто движется
        {
            sections.add(0, head);                  //добавили новую голову
            sections.remove(sections.size() - 1);   //удалили последний элемент с хвоста
        }
    }
    public void move(){
        if(isAlive()) {
            if (direction == SnakeDirection.UP) {
                move(0, -1);
            }
            if (direction == SnakeDirection.RIGHT) {
                move(1, 0);
            }
            if (direction == SnakeDirection.DOWN) {
                move(0, 1);
            }
            if (direction == SnakeDirection.LEFT) {
                move(-1, 0);
            }

        }else {
            isAlive = false;
        }
    }
    public void checkBorders(SnakeSection head){
        if ((head.getX() < 0 || head.getX() >= Room.game.getWidth()) || head.getY() < 0 || head.getY() >= Room.game.getHeight()) {
            isAlive = false;
        }
    }
    public void checkBody(SnakeSection head){
        if(sections.contains(head)){
            isAlive = false;
        }
    }
    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public List<SnakeSection> getSections() {
        return sections;
    }
}
