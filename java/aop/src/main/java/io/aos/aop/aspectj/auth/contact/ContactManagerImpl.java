package io.aos.aop.aspectj.auth.contact;

import io.aos.aop.aspectj.auth.annotation.RequiresProfile;
import io.aos.aop.aspectj.auth.annotation.WithUserProfileVerification;
import io.aos.aop.aspectj.auth.model.Person;

import java.util.HashSet;
import java.util.Set;

import static io.aos.aop.aspectj.auth.model.UserProfile.ADMIN;
import static io.aos.aop.aspectj.auth.model.UserProfile.USER;

@WithUserProfileVerification
public class ContactManagerImpl implements ContactManager {

    private final Set<Person> contacts = new HashSet<Person>();

    @RequiresProfile(ADMIN)
    public ContactManager add(Person person) {
        contacts.add(person);
        return this;
    }

    @RequiresProfile(ADMIN)
    public ContactManager remove(Person person) {
        contacts.remove(person);
        return this;
    }

    @RequiresProfile(USER)
    public Person lookup(String name) {
        for (Person person : contacts) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }

}
