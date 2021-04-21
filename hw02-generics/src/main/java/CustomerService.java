import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> customerMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> copyCustomerMap = customerMap.firstEntry();
        return new AbstractMap.SimpleImmutableEntry<>(new Customer(copyCustomerMap.getKey()), copyCustomerMap.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Customer nextCustomer = customerMap.higherKey(customer);
        return nextCustomer == null ?
            null : new AbstractMap.SimpleImmutableEntry<>(new Customer(nextCustomer), customerMap.get(nextCustomer));
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer, data);
    }
}
