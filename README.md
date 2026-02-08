> ### Record a short video (5 - 10 minutes) where demonstrate the functionality your Web Application and publish it in your [<u>YouTube</u>](https://www.youtube.com) channel.

***

# Java Advanced with Spring
## ToDo List — assignment for students

Goal: implement everything related to the ToDo entity — model, service, and unit tests. Also, honor entity contracts based on natural keys (see “Contracts and rationale” below).

What you need to do
- ToDo model (class `com.softserve.itacademy.model.ToDo`).
- ToDo service: the interface is provided (`ToDoService`), implement an in‑memory version (`ToDoServiceImpl`).
- Additionally: tests must verify natural‑key contracts and edge cases.

Contracts and rationale (natural keys)
- In this sprint we don’t use a database, so entity identity is determined by “natural keys” — stable business attributes instead of `id`.

- User
  - Natural key: `email` (unique, must be normalized with `trim + toLowerCase(Locale.ROOT)`).
  - Contract: `equals/hashCode` only by `email`. `toString` must not include `password`.

- Task
  - Natural key within a specific list: `name` (the `Task` model uses equality by `name`).
  - Task service contract: update/delete operations are performed in the context of a specific `ToDo` (pair `(todo, taskName)`), duplicate task names within a single `ToDo` are forbidden.

- ToDo (your task)
  - Natural key: pair `(owner, title)` — a user cannot have two ToDos with the same title.
  - Recommendation: implement `equals/hashCode` in the `ToDo` model using `owner` and `title` (owner is defined by `email`, consider normalizing `title` with `trim`; you may keep original spacing if needed).

ToDoService contract (expected behavior)
- `ToDo addTodo(ToDo todo, User user)`
  - Returns the added ToDo or `null` if input is invalid or a ToDo with the same natural key `(user, title)` already exists.
  - Must set the owner (`owner = user`).

- `ToDo updateTodo(ToDo todo)`
  - Updates an existing ToDo by the natural key `(owner, title)`. Returns `null` if not found or input is invalid.

- `void deleteTodo(ToDo todo)`
  - Deletes a ToDo by its natural key. No‑op for `null`/absent items.

- `List<ToDo> getAll()`
  - Returns a copy of the internal list (shallow copy) — caller modifications must not affect the storage.

- `List<ToDo> getByUser(User user)`
  - Returns all lists for the given user; for `null` — an empty list.

- `ToDo getByUserTitle(User user, String title)`
  - Returns a specific ToDo by `(user, title)` or `null` if not found or arguments are invalid.

Unit test requirements
- Use plain JUnit 5. 
- Instantiate `ToDoServiceImpl` directly; create fake dependencies manually if needed.
- Cover both happy paths and edge cases:
  - add: successful add; reject `null`; forbid duplicates `(owner, title)`.
  - update: update existing; handle `null`/not found.
  - delete: do not fail on missing; delete correctly.
  - getAll: must return a copy of the collection.
  - getByUser / getByUserTitle: correct results and behavior with `null`.

Implementation tips
- Prefer Lombok with an explicit annotation set for models, e.g.:
  - `@Getter`, `@Setter`, `@NoArgsConstructor`, `@ToString(exclude = {...})`, `@EqualsAndHashCode(of = {...})`.
- `User` already uses uniqueness by normalized `email`. Respect this convention when using `ToDo.owner`.
- Service implementation is in‑memory using collections; ensure no duplicates by the natural key and always return copies when exposing collections.

How to run tests
```
mvn -q test
```

Additional course requirements
- Implement getters/setters (or use Lombok).
- Keep a user list inside `UserService` (already done in the example); similarly, keep internal collections inside `ToDoServiceImpl`.
