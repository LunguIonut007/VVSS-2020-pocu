package pizzashop;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.MenuDataModel;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationTestR {

    private PizzaService service;

    private PaymentRepository repoPay;

    private MenuRepository repoMenu;

    @BeforeEach
    public void setUp()
    {
        repoPay = new PaymentRepository();
        repoMenu = mock(MenuRepository.class);
        service = new PizzaService(repoMenu,repoPay);
    }

    @Test
    public void test1(){
        MenuDataModel model1 = new MenuDataModel("ss",1,2.3);
        MenuDataModel model2 = new MenuDataModel("sss",2,2.5);
        when(repoMenu.getMenu()).thenReturn(Arrays.asList(model1,model2));
        assert service.getMenuData().size() == 2;
    }
}
