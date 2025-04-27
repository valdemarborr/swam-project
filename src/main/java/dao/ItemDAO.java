package dao;

import jakarta.enterprise.context.RequestScoped;
import model.Item;

@RequestScoped
public class ItemDAO extends BaseDAO<Item> {
    public ItemDAO() {
        super(Item.class);
    }
}
