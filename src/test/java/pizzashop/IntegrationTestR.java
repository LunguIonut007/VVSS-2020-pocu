package pizzashop;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class IntegrationTestR {

    private PizzaService service;

    private PaymentRepository repoPay;

    private MenuRepository repoMenu;

    @BeforeEach
    public void setUp()
    {
        repoPay = spy(new PaymentRepository(new File("file.txt")));
        repoMenu = mock(MenuRepository.class);
        service = new PizzaService(repoMenu,repoPay);
    }

    @Test
    public void testMenuData(){
        MenuDataModel model1 = new MenuDataModel("ss",1,2.3);
        MenuDataModel model2 = new MenuDataModel("sss",2,2.5);
        doNothing().when(repoPay).writeAll();
        when(repoMenu.getMenu()).thenReturn(Arrays.asList(model1,model2));
        assert service.getMenuData().size() == 2;
    }
    @Test
    public void testTotalAmount(){
        doNothing().when(repoPay).writeAll();
        when(repoPay.getAll()).thenReturn(Collections.singletonList(new Payment(1, PaymentType.Cash, 22.2)));
        assert service.getTotalAmount(PaymentType.Cash) == 22.2;
    }
}
