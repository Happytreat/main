package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.Entity;
import seedu.address.model.UniqueList;
import seedu.address.model.group.Group;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 * #TODO: Remove setGroups method and combine 2 constructors into 1 (by v1.3)
 */
public class Person extends Entity {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Timetable timetable;
    private final Set<Tag> tags = new HashSet<>();
    private final String format;
    private final String storedLocation;
    private UniqueList<Group> groups;

    /**
     * Every field must be present and not null. creates a person with timetable
     * Initialises groups as empty list.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
        String format, String storedLocation, String timetableString) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;

        this.groups = new UniqueList<>();

        this.tags.addAll(tags);
        if (format.equals("default")) {
            this.format = "horizontal";
        } else {
            this.format = format;
        }
        if (storedLocation.equals("default")) {
            this.storedLocation = new File("").getAbsolutePath().replace("\\", "/")
                + "/data/timetable";
            File directory = new File(this.storedLocation);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        } else {
            this.storedLocation = storedLocation.replace("\\", "/");
        }
        this.timetable = new Timetable(this.storedLocation + "/"
            + this.hashCode(),
            this.format,
            timetableString);
    }

    /**
     * Every field must be present and not null. creates a person with timetable
     * Initialises groups as empty list.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, UniqueList<Group> groups,
                  String format, String storedLocation, String timetableString) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;

        this.groups = new UniqueList<>();
        this.groups.setElements(groups.asUnmodifiableObservableList());

        this.tags.addAll(tags);
        if (format.equals("default")) {
            this.format = "horizontal";
        } else {
            this.format = format;
        }
        if (storedLocation.equals("default")) {
            this.storedLocation = new File("").getAbsolutePath().replace("\\", "/")
                    + "/data/timetable";
            File directory = new File(this.storedLocation);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        } else {
            this.storedLocation = storedLocation.replace("\\", "/");
        }
        this.timetable = new Timetable(this.storedLocation + "/"
                + String.valueOf(this.hashCode()),
                this.format,
                timetableString);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getFormat() {
        return format;
    }

    public String getStoredLocation() {
        return storedLocation;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public List<Group> getGroups() {
        return groups.asUnmodifiableObservableList();
    }

    public void setGroups(List<Group> groupList) {
        this.groups = new UniqueList<>();
        this.groups.setElements(groupList);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if
     * modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is
     * the same. This defines a weaker notion of equality between two persons.
     */
    @Override
    public boolean isSame(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
            && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail()
            .equals(getEmail()));
    }

    /**
     * Returns true if both persons have the same identity and data fields. This defines a stronger
     * notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
            && otherPerson.getPhone().equals(getPhone())
            && otherPerson.getEmail().equals(getEmail())
            && otherPerson.getAddress().equals(getAddress())
            && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Phone: ")
            .append(getPhone())
            .append(" Email: ")
            .append(getEmail())
            .append(" Address: ")
            .append(getAddress())
            .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
