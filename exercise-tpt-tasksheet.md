# Exercise Table-Per-Type Refactor Tasksheet

## Goal
Refactor exercises to Table Per Type (TPT) with a base `Exercise` table and per-type tables, and implement Strategy + Factory Method to handle type-specific logic cleanly.

## Scope
- JPA entities, repositories, service layer, and API DTOs
- Data migration and rollback plan
- Validation and test coverage

## Checklist
- [ ] Map current exercise usage across controller/service/repository/DTOs
- [ ] Design TPT entity model and relationships
- [ ] Define Strategy interface + per-type strategies
- [ ] Create Factory Method to select strategies by `ExerciseType`
- [ ] Update DTO/API contracts and validation rules
- [ ] Plan and implement migration + rollback
- [ ] Add tests and verify behavioral parity
- [ ] Rollout checklist and monitoring

## Step-by-step Tasks

### 1) Inventory current behavior
- [ ] Read `src/main/java/com/lazish/quiz/exercise/Exercise.java` to list fields and nullable usage.
- [ ] Trace create/update/read flows in `src/main/java/com/lazish/quiz/exercise/ExerciseServiceImpl.java`.
- [ ] Review API contracts in `src/main/java/com/lazish/quiz/exercise/ExerciseController.java` and `src/main/java/com/lazish/quiz/exercise/ExerciseDTO.java`.
- [ ] Check lesson/topic aggregation in `src/main/java/com/lazish/quiz/lesson/LessonDTO.java` and `src/main/java/com/lazish/quiz/lesson/LessonController.java`.
- [ ] Document type-specific fields and any implicit rules per `ExerciseType`.

### 2) Design TPT entities
- [ ] Define base entity `Exercise` with shared fields and `@Inheritance(strategy = InheritanceType.JOINED)`.
- [ ] Create subtype entities per `ExerciseType` (e.g., `ListeningExercise`, `SpeakingExercise`, `TranslateExercise`, `MatchingExercise`).
- [ ] Decide if subtype tables use shared primary key with `@PrimaryKeyJoinColumn`.
- [ ] Specify relationships (lesson/topic, media, etc.) at the base or subtype.
- [ ] Update enums or mapping rules if new types are added.

### 3) Strategy Pattern for type-specific logic
- [ ] Define `ExerciseStrategy` interface (create/update/validate/map operations).
- [ ] Implement one strategy per subtype (e.g., `ListeningExerciseStrategy`).
- [ ] Centralize validation rules so each strategy handles its own invariants.
- [ ] Decide how strategies map DTO payloads to subtype entities.

### 4) Factory Method for strategy selection
- [ ] Create a factory (e.g., `ExerciseStrategyFactory`) to return strategy by `ExerciseType`.
- [ ] Define selection rules for unknown/unsupported types.
- [ ] Integrate factory usage in `ExerciseServiceImpl` so core flow is type-agnostic.

### 5) DTO and API contract updates
- [ ] Choose DTO design:
  - [ ] Polymorphic DTO with a `type` field + per-type payload section
  - [ ] Separate DTOs per type (explicit endpoints or discriminator)
- [ ] Define validation rules for each payload type.
- [ ] Update controllers to call strategy-driven service methods.
- [ ] Update response mapping for list/detail endpoints.

### 6) Repository and mapping updates
- [ ] Add subtype repositories if needed, or use base repository with joins.
- [ ] Update `ExerciseMapper` to handle subtype mapping.
- [ ] Ensure lazy/eager loading decisions are consistent with current API needs.

### 7) Database migration plan
- [ ] Decide migration tooling (Flyway/Liquibase) or manual SQL.
- [ ] Create tables for subtype entities with FK to base `exercises`.
- [ ] Backfill subtype tables from existing nullable columns by `ExerciseType`.
- [ ] Verify data consistency and nullability constraints.
- [ ] Define rollback steps (merge back or keep old columns temporarily).

### 8) Backward compatibility and rollout
- [ ] Decide if old fields remain during transition (dual-write/dual-read).
- [ ] Add feature flag or endpoint versioning if needed.
- [ ] Define rollback trigger and monitoring metrics.

### 9) Tests
- [ ] Unit tests for each strategy (create/update/validate).
- [ ] Factory tests for type-to-strategy mapping.
- [ ] Repository tests for JOINED inheritance queries.
- [ ] Controller tests for DTO validation and responses.
- [ ] Migration tests for data backfill correctness.

## Open Questions
- [ ] Should API be backward-compatible or cut over with versioning?
- [ ] Prefer polymorphic DTO vs per-type DTO endpoints?
- [ ] Migration approach: Flyway/Liquibase or direct SQL scripts?
- [ ] Any performance constraints requiring fetch optimization?

## Rollout Checklist
- [ ] Migration applied in staging and verified
- [ ] API responses match legacy behavior
- [ ] Data integrity checks complete
- [ ] Monitoring in place for errors and performance regressions
- [ ] Rollback plan validated

