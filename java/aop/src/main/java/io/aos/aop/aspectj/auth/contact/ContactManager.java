package io.aos.aop.aspectj.auth.contact;

import io.aos.aop.aspectj.auth.model.Person;

public interface ContactManager {

    public ContactManager add(Person person);

    public ContactManager remove(Person person);

    public Person lookup(String name);

}
