package io.aos.reflect.type;

import io.aos.reflect.type.TypeResolver;

import java.io.Serializable;
import java.lang.reflect.Type;

import org.testng.annotations.Test;

@Test
public class Examples {
  static class Device {
  }

  static class Router extends Device {
  }

  static class GenericDAO<T, ID extends Serializable> {
    protected Class<T> persistentClass;
    protected Class<ID> idClass;

    @SuppressWarnings("unchecked")
    private GenericDAO() {
      Class<?>[] typeArguments = TypeResolver.resolveArguments(getClass(), GenericDAO.class);
      this.persistentClass = (Class<T>) typeArguments[0];
      this.idClass = (Class<ID>) typeArguments[1];
    }
  }

  static class DeviceDAO<T extends Device> extends GenericDAO<T, Long> {
  }

  static class RouterDAO extends DeviceDAO<Router> {
  }

  public void shouldResolveLayerSuperTypeInfo() {
    RouterDAO routerDAO = new RouterDAO();
    assert routerDAO.persistentClass == Router.class;
    assert routerDAO.idClass == Long.class;
  }

  static class Entity<ID extends Serializable> {
    ID id;

    void setId(ID id) {
    }
  }

  static class SomeEntity extends Entity<Long> {
  }

  public void shouldResolveTypeFromFieldDeclaration() throws Exception {
    Type fieldType = Entity.class.getDeclaredField("id").getGenericType();
    assert TypeResolver.resolveClass(fieldType, SomeEntity.class) == Long.class;
  }

  public void shouldResolveTypeFromMethodDeclaration() throws Exception {
    Type mutatorType = Entity.class.getDeclaredMethod("setId", Serializable.class)
        .getGenericParameterTypes()[0];
    assert TypeResolver.resolveClass(mutatorType, SomeEntity.class) == Long.class;
  }
  
}
