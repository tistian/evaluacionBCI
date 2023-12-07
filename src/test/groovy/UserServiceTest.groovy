import com.api.evaluacion.model.Phone
import com.api.evaluacion.model.User
import com.api.evaluacion.repository.PhoneRepository
import com.api.evaluacion.repository.UserRepository
import com.api.evaluacion.rest.request.PhoneRequest
import com.api.evaluacion.rest.request.CreateRequest
import com.api.evaluacion.rest.request.UpdateRequest
import com.api.evaluacion.rest.response.ErrorException
import com.api.evaluacion.rest.response.CreateResponse
import com.api.evaluacion.rest.response.UserResponse
import com.api.evaluacion.service.UserService
import com.api.evaluacion.service.UserServiceImpl
import org.modelmapper.ModelMapper
import spock.lang.Specification
import spock.lang.Unroll

import java.text.SimpleDateFormat


class UserServiceTest extends Specification {

    UserService userService;

    UserRepository mockUserRepository = Mock();
    PhoneRepository mockPhoneRepository = Mock();
    ModelMapper mockModelMapper = Mock();

    def setupSpec() {
        //corre una vez al inicio de proceso
    }

    def setup() {
        //Codigo que corre antes de cada test
        userService = new UserServiceImpl(userRepository: mockUserRepository, modelMapper: mockModelMapper, phoneRepository: mockPhoneRepository);

    }

    def cleanup() {
        //Codigo que corre despues de cada test
    }

    def "crearUsuarioCorrectamente"() {
        given:

        CreateResponse createResponse = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        User user = new User();

        user.setCreated(dateFormat.parse("01/01/2020"));
        user.setLastLogin(dateFormat.parse("01/01/2020"));
        user.setToken("token");
        user.setActive(false);
        user.setId(2L);
        user.setEmail("email");
        user.setPassword("password");


        CreateRequest request = new CreateRequest();
        request.setName("name");
        request.setEmail("email");
        request.setPassword("password");
        PhoneRequest phoneRequest = new PhoneRequest();
        phoneRequest.setNumber(0L);
        Phone phones = new Phone();
        phones.setNumber(0L);
        mockUserRepository.findByEmail(_) >> new ArrayList<User>();
        mockUserRepository.save(_) >> user;

        when:
        createResponse = userService.create(request);


        then:
        1 * mockModelMapper.map(_, _) >> { CreateRequest request2, User user3 -> // verifica que el mock del mapper se llama una vez con el User y cualquier UserResponse
            user3.email = request2.email // asigna los atributos del User al UserResponse
            user3.password = request2.password
            user3.phones = Arrays.asList(phones)
            user3.name = request2.name
        }
        1 * mockModelMapper.map(_, _) >> { User user2, CreateResponse response -> // verifica que el mock del mapper se llama una vez con el User y cualquier UserResponse
            response.id = user2.id // asigna los atributos del User al UserResponse
            response.created = user2.created
            response.lastLogin = user2.lastLogin
            response.token = user2.token
        }
        and:
        createResponse.getId() == user.getId();
        createResponse.getCreated().toString().equals(user.getCreated().toString());
        createResponse.getToken().equals(user.getToken());
        createResponse.getLastLogin().toString().equals(user.getLastLogin().toString());

    }

    def "crearUsuarioYaExisteException"() {
        given:

        CreateResponse createResponse = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        User user = new User();
        user.setCreated(dateFormat.parse("01/01/2020"));
        user.setLastLogin(dateFormat.parse("01/01/2020"));
        user.setToken("token");
        user.setActive(false);
        user.setId(2L);
        user.setEmail("email");
        user.setPassword("password");

        CreateRequest request = new CreateRequest();
        request.setName("name");
        request.setEmail("email");
        request.setPassword("password");
        PhoneRequest phoneRequest = new PhoneRequest();
        phoneRequest.setNumber(0L);
        request.setPhones(Arrays.asList(phoneRequest));
        mockUserRepository.findByEmail(_) >> Arrays.asList(user);
        mockUserRepository.save(_) >> user;


        when:
        userService.create(request);


        then:
        def e = thrown(ErrorException)
        e.detail == "Usuario ya existe"
    }

    @Unroll
    def "actualizarUsuarioCorrectamente"() {
        given:

        CreateResponse createResponse = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        User user = new User();

        user.setCreated(dateFormat.parse("01/01/2020"));
        user.setLastLogin(dateFormat.parse("01/01/2020"));
        user.setToken("token");
        user.setActive(false);
        user.setId(2L);
        user.setEmail("email");
        user.setPassword("password");

        UpdateRequest request = new UpdateRequest();
        request.setName("name");
        request.setEmail("email");
        request.setPassword("password");
        PhoneRequest phoneRequest = new PhoneRequest();
        phoneRequest.setNumber(0L);
        Phone phones = new Phone();
        phones.setNumber(0L);
        mockUserRepository.findByEmail(_) >> Arrays.asList(user);

        when:
        createResponse = userService.update(request);


        then:
        1 * mockModelMapper.map(_, _) >> { UpdateRequest request2, User user3 -> // verifica que el mock del mapper se llama una vez con el User y cualquier UserResponse
            user3.email = request2.email // asigna los atributos del User al UserResponse
            user3.password = request2.password
            user3.phones = Arrays.asList(phones)
            user3.name = request2.name
            user3.active = request2.active
        }
        and:
        noExceptionThrown();

    }


    def "actualizarUsuarioNoExisteException"() {
        given:


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        User user = new User();
        user.setCreated(dateFormat.parse("01/01/2020"));
        user.setLastLogin(dateFormat.parse("01/01/2020"));
        user.setToken("token");
        user.setActive(false);
        user.setId(2L);
        user.setEmail("email");
        user.setPassword("password");

        UpdateRequest request = new UpdateRequest();
        request.setName("name");
        request.setEmail("email");
        request.setPassword("password");
        PhoneRequest phoneRequest = new PhoneRequest();
        phoneRequest.setNumber(0L);
        request.setPhones(Arrays.asList(phoneRequest));
        mockUserRepository.findByEmail(_) >> new ArrayList<User>();

        when:
        userService.update(request);


        then:
        def e = thrown(ErrorException)
        e.detail == "Usuario no existe"
    }


    def "readUsuarioCorrectamente"() {
        given:

        UserResponse userResponse = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        User user = new User();

        user.setCreated(dateFormat.parse("01/01/2020"));
        user.setLastLogin(dateFormat.parse("01/01/2020"));
        user.setToken("token");
        user.setActive(false);
        user.setId(2L);
        user.setEmail("email");
        user.setPassword("password");


        mockUserRepository.findByEmail(_) >> Arrays.asList(user);

        when:
        userResponse = userService.read("email");

        then:
        1 * mockModelMapper.map(_, _) >> { User user2, UserResponse userResponse2 -> // verifica que el mock del mapper se llama una vez con el User y cualquier UserResponse
            userResponse2.email = user2.email // asigna los atributos del User al UserResponse
            userResponse2.password = user2.password
            userResponse2.name = user2.name
            userResponse2.active = user2.active
            userResponse2.lastLogin = user2.lastLogin
        }
        and:
        userResponse.getName() == user.getName();
        userResponse.getPassword() == user.getPassword();
        userResponse.getLastLogin() == user.getLastLogin();
        userResponse.active == user.active;
        userResponse.getEmail() == user.getEmail();
    }

    def "readUsuarioNoExisteException"() {
        given:

        UserResponse userResponse = null;
        mockUserRepository.findByEmail(_) >> new ArrayList<User>();
        when:
        userResponse = userService.read("email");
        then:
        def e = thrown(ErrorException)
        e.detail == "Usuario no existe"
    }

    @Unroll
    def "deleteUsuarioCorrectamente"() {
        given:

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        User user = new User();

        user.setCreated(dateFormat.parse("01/01/2020"));
        user.setLastLogin(dateFormat.parse("01/01/2020"));
        user.setToken("token");
        user.setActive(false);
        user.setId(2L);
        user.setEmail("email");
        user.setPassword("password");

        mockUserRepository.findByEmail(_) >> Arrays.asList(user);

        when:
        userService.delete("email");
        then:
        noExceptionThrown();

    }


    def "deleteUsuarioNoExisteException"() {
        given:

        mockUserRepository.findByEmail(_) >> new ArrayList<User>();
        when:
        userService.delete("email");
        then:
        def e = thrown(ErrorException)
        e.detail == "Usuario no existe"
    }

}
