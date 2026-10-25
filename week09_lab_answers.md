# WEBDEV2 — Week 9 Lab Answers

Name: Jade Roland Eduard C Pinon
Student ID: 2511036
GitHub repo: https://github.com/<arcticWulf123>/webdev2
Date submitted:

---

## Task 1 — One-to-One + One-to-Many Mappings

**Approach / explanation:**
I set the owning sides by giving them the @JoinColumn annotation, I applied these on the Employee and the User class. While on the other hand, I used the mappedBy annotation to UserProfile and Department classes, I also added the helper methods in the Department class such as addEmployee(), removeEmployee() and findAll().

**Code / endpoint(s) used (if applicable):**
```java
@Entity 
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne 
    @JoinColumn(name = "profile_id")
    private UserProfile profile;
}

@Entity 
public class UserProfile {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "profile")
    private User user;
}

@Entity 
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setDepartment(null);
    }
}

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Employee(Long id, Department department) {
        this.id = id;
        this.department = department;
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
```

**Evidence (screenshot filename, request/response sample, or file reference in this folder):**
![Task 1 save log](screenshots/task1_save_log.png)

**Reflection question:**
_Which entity owns the `Department`–`Employee` relationship and why, and what value must `mappedBy` contain?_

Your answer:
_[Write your answer here.]_

---

## Task 2 — Many-to-Many Join Table + Association Entity

**Approach / explanation:**
_[Write 2–4 sentences describing your `@JoinTable` mapping and why you replaced it with `Enrollment`.]_

**Code / endpoint(s) used (if applicable):**
```java
// paste your Java/Spring Boot code, or relevant snippet, here
```

**Evidence (screenshot filename, request/response sample, or file reference in this folder):**
_[e.g. `task2_enroll_ok.png`, `task2_duplicate_error.png`, or paste both responses here.]_

**Reflection question:**
_When must a many-to-many relationship be modeled as an association entity instead of a direct `@ManyToMany`, and what did the unique constraint protect in your test?_

Your answer:
_[Write your answer here.]_

---

## Task 3 — Fetch Strategies Experiment — LAZY vs EAGER

**Approach / explanation:**
_[Write 2–4 sentences describing how you reproduced the exception and the two fixes you compared.]_

**Code / endpoint(s) used (if applicable):**
```java
// paste your Java/Spring Boot code, or relevant snippet, here
```

**Evidence (screenshot filename, request/response sample, or file reference in this folder):**
_[e.g. `task3_lazy_exception.png`, `task3_sql_compare.png`, or paste the trace + SQL counts here.]_

**Reflection question:**
_What causes `LazyInitializationException`, and why is prefer-LAZY plus explicit fetch join/`@EntityGraph` preferable to marking everything EAGER?_

Your answer:
_[Write your answer here.]_

---

## Task 4 — Cascade + orphanRemoval — Safe vs. Dangerous

**Approach / explanation:**
_[Write 2–4 sentences describing your safe cascade choice, the orphan-removal test, and why REMOVE was avoided for shared entities.]_

**Code / endpoint(s) used (if applicable):**
```java
// paste your Java/Spring Boot code, or relevant snippet, here
```

**Evidence (screenshot filename, request/response sample, or file reference in this folder):**
_[e.g. `task4_orphan_deleted.png`, `task4_course_survives.png`, or paste before/after SELECT output here.]_

**Reflection question:**
_What is the difference between `CascadeType.REMOVE` and `orphanRemoval = true`, and in which situation is each appropriate or dangerous?_

Your answer:
_[Write your answer here.]_

---

## Task 5 — Capstone Milestone 2 — ERD to JPA Entity Model

**Approach / explanation:**
_[Write 2–4 sentences describing how you translated your ERD entities, cardinalities, owning sides, and FK/join tables.]_

**Code / endpoint(s) used (if applicable):**
```java
// paste your Java/Spring Boot code, or relevant snippet, here
```

**Evidence (screenshot filename, request/response sample, or file reference in this folder):**
_[e.g. `task5_erd.png`, `task5_smoke_test.png`, or paste the ERD→entity table + passing test log here.]_

**Reflection question:**
_For one relationship in your capstone model, state the cardinality, the owning side, where the FK/join table lives, and why you chose that fetch and cascade setting._

Your answer:
_[Write your answer here.]_

---

## Self-Check

- [ ] All Week 9 work on the `week9` branch (project at branch root, no `week9/` folder)
- [ ] This file completed and renamed `answers.md`
- [ ] Repository link pasted into Moodle (no files uploaded)
