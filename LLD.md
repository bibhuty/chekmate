# 🔎 Attention to Details:
- [ ] **Abbreviated error handling** → No empty catches, vague logs, or TODOs. Every error path must define recovery or fail-fast.  
- [ ] **Memory leaks** → All resources (DB connections, sockets, file handles, listeners) are closed or released.  
- [ ] **Race conditions** → Shared state is protected (atomic ops, locks, immutability) to avoid concurrent corruption.  
- [ ] **Inconsistent naming** → One concept = one name across modules. No synonyms (e.g., `customerId` vs `clientId`).  

# 📋 Naming Checklist:

## Intention-Revealing
- [ ] Does each name clearly reveal **why it exists, what it does, and how it is used**?
- [ ] If a comment exist for that name, would the name still make sense if comments were stripped out?
## Avoid Disinformation
- [ ] Are misleading names avoided (`accountList` when it’s not actually a list)?
- [ ] Are small/confusing variations avoided (`custId` vs `customerId`, `getAccount()` vs `getAccounts()`)?
## Meaningful Distinctions
- [ ] Are meaningless distinctions avoided (`Product`, `ProductInfo`, `ProductData`)?
- [ ] Are number-series names avoided (`a1`, `a2`, …)?
- [ ] Are noise words avoided (`the`, `data`, `info`)?
## Pronounceable & Searchable
- [ ] Are all names pronounceable (`generationTimestamp` not `genymdhms`)?
- [ ] Are names searchable (avoid single-letter names outside tiny local scopes)?
- [ ] Are constants named in `UPPER_CASE_WITH_UNDERSCORES`?

## Avoid Encodings
- [ ] Is scope/type encoding avoided (`m_dsc`, `phoneString`)?
- [ ] Are interface/implementation names clearly distinguished (`PaymentService` vs `PaymentServiceImpl`)?

## Classes & Methods
- [ ] Do classes/types use nouns or noun phrases?
- [ ] Do functions/methods use verbs or verb phrases?
- [ ] Are overloaded constructors replaced with descriptive static factory methods?
- [ ] Do methods use clear, unambiguous verbs (`kill`, `abort` instead of slang)?

## Consistency
- [ ] Is one word reserved for one concept only (e.g., choose `fetch` OR `get` OR `retrieve`)?
- [ ] Is the same word avoided for different concepts (e.g. don’t use `add` for both concatenation and collection insertion)?

## Context & Domain
- [ ] Is the right balance struck between **solution domain names** (CS terms) and **problem domain names** (business terms)?
- [ ] Are names placed in proper context (grouped in classes/functions/namespaces)?
- [ ] Is unnecessary context avoided (`addrFirstName` inside `Address` class)?
- [ ] Are abbreviations avoided unless universally understood (`URL`, `HTML`)?

---


# 🧩 Functions Checklist:

## Small!
- [ ] Functions are ≤ 20 lines.
- [ ] Blocks within `if` / `else` / `while` are a single line (delegating to another function).
- [ ] Indent level does not exceed 1–2 levels.
## Do One Thing
- [ ] Each function does only one thing.
- [ ] All statements in the body are at the same level of abstraction.
- [ ] Function bodies read like a **table of contents** (TOC).
- [ ] If you can extract a sub-function with a meaningful new name, the original was doing more than one thing.
## Switch Statements / Multi-if Chains
- [ ] No repeated `switch` or multi-if chains.
- [ ] Polymorphism or strategy objects bury each case in a low-level class.
- [ ] High-level orchestration remains closed to modification (OCP).
## Descriptive Names
- [ ] Functions have descriptive names; longer and explicit is better than short and cryptic.
- [ ] Function names use readable multi-word conventions (camelCase / PascalCase).
- [ ] Prefer a descriptive function name over a descriptive comment.
## Function Arguments
- [ ] Niladic (0 args) or Monadic (1 arg) preferred.
- [ ] Dyadic (2 args) allowed only when natural (e.g. `assertExpectedEqualsActual`).
- [ ] Triadic (3 args) are a code smell; refactor if possible.
- [ ] Polyadic (>3 args) forbidden unless justified.
- [ ] Related args are grouped into value objects or classes.
- [ ] Argument lists (rest parameters) are used only when items are treated identically.
- [ ] No **flag arguments** (boolean switches that change behavior).
- [ ] No **output arguments**; functions return values instead.
- [ ] Input arguments are not mutated (unless the function name clearly advertises in-place mutation).
## Side Effects
- [ ] No hidden side effects (no unexpected state changes).
- [ ] If mutation is required, it happens only on the owning object and is obvious from the function name.
- [ ] No “double-take” code (no surprises that force rereading).
## Command–Query Separation
- [ ] Functions either **do something** (command) or **answer something** (query).
- [ ] No mixing of command + query in the same function.

## Exceptions
- [ ] Functions do not return error codes (no `if (deletePage(page) == E_OK)`).
- [ ] Error handling is its own concern; a function that handles errors does nothing else.
- [ ] No centralised `ErrorCode` enum (dependency magnet).
- [ ] Domain errors are modeled with typed exceptions (or typed results), not codes.

## Temporal Coupling
- [ ] APIs do not require methods to be called in a specific order.
- [ ] If sequencing is needed, enforce it with constructors, factories, or state machines.
## Structured Programming
- [ ] Functions and blocks have one entry and one exit.
- [ ] Only one `return`, no `break` / `continue` in loops — except in very small functions where multiple exits are clearer.

---

# 📝 Comments Checklist

**Rule of Thumb:** The best comment is the one you didn’t need to write — because the code was expressive enough.  
Write comments only when they add clarity about **intent, constraints, or consequences**.  

### ✅ Good Comments
- [ ] **Informative** – add missing context (regex format, units, data ranges).  
- [ ] **Intent** – explain *why* (e.g., intentionally using `Promise.all` to reproduce race).  
- [ ] **Clarification** – highlight language quirks (`NaN !== NaN`, `typeof null`).  
- [ ] **Warning of Consequence** – call out dangerous/test-only behavior.  
- [ ] **TODO / FIXME / HACK** – track known debt or future work.  
- [ ] **Amplification** – emphasize subtle but crucial operations (`.toLowerCase()` before compare).  
- [ ] **Public API Docs** – use TSDoc/JSDoc for exported functions, not private helpers.  

### ❌ Bad Comments
- [ ] **Mumbling** – vague/unclear (`// uh, handle error somehow`).  
- [ ] **Redundant** – restates code (`// increment i by 1`).  
- [ ] **Misleading** – out of sync with implementation.  
- [ ] **Mandated Boilerplate** – pointless `@param`/`@return` repeating obvious info.  
- [ ] **Noise** – emotional filler, jokes, frustration.  
- [ ] **Journal / Bylines** – history or authorship (Git handles this).  
- [ ] **Position Markers** – visual clutter (`//// Section ////`).  
- [ ] **Closing Brace Comments** – `} // end if`.  
- [ ] **Commented-Out Code** – delete, VCS is history.  
- [ ] **HTML in TSDoc** – let tools handle styling.  
- [ ] **Non-local Information** – references to external docs/paths that rot.  
- [ ] **Too Much Information** – essays or design papers inline (link ADR instead).  
- [ ] **Inobvious Connection** – unclear tie between comment & code.  
- [ ] **Scary Noise** – dramatic but unhelpful (`// DANGER!!!`).  
- [ ] **Function Headers** – repeating signatures in prose.  

---


# 🖋️ Formatting Checklist (Clean Code – Chapter 5)

### Purpose
Code formatting is about **communication**, and communication is the professional developer's first order of business.  

---

## Vertical Formatting
- [ ] **File Size** – Files are ~200–500 lines (not a hard limit).  
- [ ] **Newspaper Metaphor** – High-level functions at top, details below, lowest-level helpers at the bottom.  
- [ ] **Vertical Openness** – Blank lines separate distinct concepts.  
- [ ] **Vertical Density** – No unnecessary blank lines between strongly related code.  
- [ ] **Vertical Distance** – Related concepts are kept close.  
  - [ ] Variables declared near usage.  
  - [ ] Loop variables declared in loop statements.  
  - [ ] Instance variables at the top of classes.  
  - [ ] Dependent functions follow newspaper metaphor.  
  - [ ] Conceptually related functions grouped together.  

---

## Horizontal Formatting
- [ ] **Line Length** – ≤100 chars (≤120 in rare justified cases).  
- [ ] **Openness/Density** – Spaces around weakly related tokens, tight for strongly related ones.  
- [ ] **Breaking Indentation** – Consistent indentation even for short `if` / `while` / `for` bodies.  
- [ ] **Dummy Scopes** – Empty loop bodies use an indented `;` for clarity.  
# 📦 Data & Abstraction Checklist

## Data Abstraction
- [ ] Does the class expose **intent/essence** instead of raw fields?  
- [ ] Are getters/setters avoided unless truly necessary?  
- [ ] Are higher-level operations provided (e.g., `translate()`, `percentRemaining()`) instead of low-level accessors?  

## Data/Object Anti-Symmetry
- [ ] Have we consciously chosen **procedural** (easy new functions, hard new types) vs **OO** (easy new types, hard new functions)?  
- [ ] Are simple data structures kept at **boundaries** (DTOs, records), and objects with behavior in the **domain core**?  
- [ ] Is the chosen style consistent for this module?  

## Law of Demeter
- [ ] Does each method only talk to:
  - Itself  
  - Objects it created  
  - Objects passed as parameters  
  - Its own fields  
- [ ] Are there **no train wrecks** (`a.getB().getC().getD()`)?  
- [ ] Is delegation/facade used instead of exposing internals?  

## Hybrids
- [ ] Is the class either a **data structure** (fields only) or an **object** (behavior + hidden state)?  
- [ ] Are hybrids (fields exposed + behavior + persistence) avoided?  
- [ ] If Active Record / ORM entities are used, is it a conscious choice for CRUD simplicity, not core domain logic?  
# ⚠️ Error Handling (LLD)

- [ ] **Happy Path Clear** → Error handling does not obscure the main intent of the function.  
- [ ] **Exceptions over Return Codes** → Do not use error codes; throw exceptions instead.  
- [ ] **Narrow Try/Catch Scopes** → Keep `try` blocks small and focused on a single transaction.  
- [ ] **Unchecked Exceptions** → Use unchecked exceptions in application code (avoid checked exceptions that force boilerplate handling).  
- [ ] **Caller-Oriented Exceptions** → Define exception classes only when the caller needs to handle them differently.  
- [ ] **Normal Flow Defined** → Handle expected cases in the function logic (special case objects, default values), not via exceptions.  
- [ ] **No Returning `null`** → Return a special case object or throw an exception instead.  
- [ ] **No Passing `null`** → Forbid `null` arguments by contract; if unavoidable, fail fast.  
- [ ] **Context-Rich Exceptions** → Every thrown exception includes enough context to locate and understand the error.  
# 🛡️ Boundary Checklist (Clean Code – Chapter 8)
- [ ] Do not expose raw collections (`Map`, `Record`, arrays) across module boundaries.  
- [ ] Public APIs return **domain-specific abstractions**, not generic types or third-party objects.  
- [ ] Maintain **learning tests** for major third-party libraries to lock down expected behavior.  
- [ ] Treat learning tests as **disposable experiments** — they test assumptions, not product logic. 
- [ ] When integrating with code that doesn’t exist yet, define your own **interface/abstraction** and build against it.  
- [ ] If the real dependency’s API doesn’t match your abstraction, introduce an **Adapter** behind your interface.  
# ✅ Unit Tests Checklist

## The Three Laws of TDD
- [ ] **First Law:** Write a failing test before writing production code.
- [ ] **Second Law:** Write only enough of a test to fail (not compiling counts as failing).
- [ ] **Third Law:** Write only enough production code to make the test pass.

---

## Keeping Tests Clean
- [ ] Tests are as important as production code → keep them simple, expressive, and maintainable.
- [ ] Tests follow **BUILD–OPERATE–CHECK** structure.
- [ ] Tests enable **fearless change** by covering production code thoroughly.
- [ ] Avoid “dirty tests” — unreadable tests are as bad as no tests.

---

## Domain-Specific Testing Language (DSTL)
- [ ] Tests use **problem-domain terms**, not low-level technical details.
- [ ] Common setup & boilerplate are hidden behind expressive helpers.
- [ ] Tests read like **business rules** (clear intent, minimal noise).

---

## A Dual Standard
- [ ] Test helpers may sacrifice efficiency (memory/CPU) for clarity.
- [ ] Never sacrifice **cleanliness** — test code must stay simple and expressive.
- [ ] Production code = efficient + clean; Test code = clear + clean (efficiency optional).

---

## Rule for Test Functions
- [ ] Test **one concept per test**.
- [ ] Multiple asserts allowed if they support the same concept.
- [ ] Never mix multiple concepts (e.g., area + perimeter in same test).

---

## F.I.R.S.T Principles
- [ ] **Fast** → Tests run quickly, or they’ll be skipped.
- [ ] **Independent** → Tests do not rely on each other’s state or order.
- [ ] **Repeatable** → Same result on any machine, CI, or environment.
- [ ] **Self-validating** → Tests give a boolean outcome (pass/fail), no manual log-checks.
- [ ] **Timely** → Write tests just before writing production code.
# 📦 Classes & Organization Checklist (Ch. 10)

## Class Organization
- [ ] Follow order:  
  1. public static constants  
  2. private static variables  
  3. private instance variables  
  4. public instance variables (rare)  
  5. public functions  
  6. private utilities (immediately after their caller)  
- [ ] Default to `private` / `protected`; loosen encapsulation only as a last resort.

## Classes Should Be Small
- [ ] Each class has **one clear responsibility**.  
- [ ] If class name feels vague (e.g., `Manager`, `Processor`), it likely has too many reasons to change.  
- [ ] Class description should fit in ≤25 words without using “and/or/but/if.”  

## Single Responsibility Principle (SRP)
- [ ] A class/module has **only one reason to change**.  
- [ ] Split reporting, storage, and orchestration responsibilities into separate classes.  

## Cohesion
- [ ] Each method should use ≥1 instance variable.  
- [ ] If methods don’t touch instance state, they likely belong elsewhere.  
- [ ] Prefer fewer instance variables; high cohesion means variables are used across most methods.  
- [ ] Split low-cohesion classes into smaller, focused ones.  

## Organizing for Change
- [ ] Don’t split classes prematurely; refactor when change forces your hand.  
- [ ] If adding a new method reveals multiple responsibilities → reorganize into smaller classes.  

## Isolating from Change
- [ ] Depend on **abstractions**, not on concrete classes.  
- [ ] Apply the **Dependency Inversion Principle (DIP)**:  
  - High-level modules should not depend on low-level modules.  
  - Both should depend on abstractions (interfaces/abstract classes).  
- [ ] Inject dependencies (constructor injection preferred).  
- [ ] Avoid tight coupling: clients should not break if implementations change.  
# 📜 LeBlanc's Law: "Later Equals Never"
A list of things we’ve intentionally deferred (to avoid forgetting):
- [ ] _______________
- [ ] _______________
- [ ] _______________

# 🧩 OO Principle(Design Patterns: Ch 01)
- [ ] Encapsulate what varies  
- [ ] Program to an interface, not an implementation  
- [ ] Favour composition over inheritance  