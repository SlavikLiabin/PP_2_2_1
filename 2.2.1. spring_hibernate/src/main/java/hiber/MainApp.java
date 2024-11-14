package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      Car car1 = new Car("Mercedes", 1);
      Car car2 = new Car("Audi", 2);
      Car car3 = new Car("BMW", 3);
      Car car4 = new Car("Volkswagen", 4);

      userService.add(new User("Анна", "Петрова", "anna.petrovа@mail.ru", car1));
      userService.add(new User("Иван", "Сидоров", "ivan.sidorov@mail.ru", car2));
      userService.add(new User("Елена", "Козлова", "elena.kozlova@mail.ru", car3));
      userService.add(new User("Максим", "Волков", "maxim.volkov@mail.ru", car4));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car = "+user.getCar());
         System.out.println();
      }

//      поиск по модели и серии
      System.out.println(userService.getUserCar("Audi", 2));

      // если пользователя с такой машиной нет
      try {
         userService.getUserCar("Lada", 2109);
      } catch (NoResultException e) {
         System.out.println("нет результата");
      }

      context.close();
   }
}
