# 🤔 Self-Review Questions
- [ ] Is the code obvious to a reader?  
- [ ] Does it look like I cared enough?  
- [ ] Is the code communicating intent clearly?  
- [ ] Would another engineer find it easy to change/extend?  
- [ ] **Boy Scout Rule:** Did I leave the ground cleaner than I found it?  
	- Rename one variable, break one large function, eliminate duplication, simplify one composite condition, etc.

# 🔎 Attention to Detail (LLD)
- [ ] **Abbreviated error handling** → No empty catches, vague logs, or TODOs. Every error path must define recovery or fail-fast.  
- [ ] **Memory leaks** → All resources (DB connections, sockets, file handles, listeners) are closed or released.  
- [ ] **Race conditions** → Shared state is protected (atomic ops, locks, immutability) to avoid concurrent corruption.  
- [ ] **Inconsistent naming** → One concept = one name across modules. No synonyms (e.g., `customerId` vs `clientId`).  

# 📋 Naming Checklist (Collected Questions)

## Intention-Revealing
- [ ] Does each name clearly reveal **why it exists, what it does, and how it is used**?
- [ ] Would the name still make sense if comments were stripped out?

## Avoid Disinformation
- [ ] Are misleading names avoided (`accountList` when it’s not actually a list)?
- [ ] Are small/confusing variations avoided (`custId` vs `customerId`)?

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
- [ ] Do classes use nouns or noun phrases?
- [ ] Do methods use verbs or verb phrases?
- [ ] Are overloaded constructors replaced with descriptive static factory methods?
- [ ] Do methods use clear, unambiguous verbs (`kill`, `abort` instead of slang)?

## Consistency
- [ ] Is one word reserved for one concept only (e.g., choose `fetch` OR `get` OR `retrieve`)?
- [ ] Is the same word avoided for different concepts (don’t use `add` for both concatenation and collection insertion)?

## Context & Domain
- [ ] Is the right balance struck between **solution domain names** (CS terms) and **problem domain names** (business terms)?
- [ ] Are names placed in proper context (grouped in classes/functions/namespaces)?
- [ ] Is unnecessary context avoided (`addrFirstName` inside `Address` class)?
- [ ] Are abbreviations avoided unless universally understood (`URL`, `HTML`)?

---


# Clean Code – Chapter 3 Checklist (Functions)

> *Master programmers think of systems as stories to be told rather than programs to be written.*

---

## Small!
- [ ] Functions are ≤ 20 lines.
- [ ] Blocks within `if` / `else` / `while` are a single line (delegating to another function).
- [ ] Indentation does not exceed 1–2 levels.

---

## Do One Thing
- [ ] Each function does only one thing.
- [ ] All statements in the body are at the same level of abstraction.
- [ ] Function bodies read like a **table of contents** (TOC).
- [ ] If you can extract a sub-function with a meaningful new name, the original was doing more than one thing.

---

## Switch Statements / Multi-if Chains
- [ ] No repeated `switch` or multi-if chains.
- [ ] Polymorphism or strategy objects bury each case in a low-level class.
- [ ] High-level orchestration remains closed to modification (OCP).

---

## Descriptive Names
- [ ] Functions have descriptive names; longer and explicit is better than short and cryptic.
- [ ] Function names use readable multi-word conventions (camelCase / PascalCase).
- [ ] Prefer a descriptive function name over a descriptive comment.

---

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

---

## Side Effects
- [ ] No hidden side effects (no unexpected state changes).
- [ ] If mutation is required, it happens only on the owning object and is obvious from the function name.
- [ ] No “double-take” code (no surprises that force rereading).

---

## Command–Query Separation
- [ ] Functions either **do something** (command) or **answer something** (query).
- [ ] No mixing of command + query in the same function.

---

## Exceptions
- [ ] Functions do not return error codes (no `if (deletePage(page) == E_OK)`).
- [ ] Error handling is its own concern; a function that handles errors does nothing else.
- [ ] No centralized `ErrorCode` enum (dependency magnet).
- [ ] Domain errors are modeled with typed exceptions (or typed results), not codes.

---

## Temporal Coupling
- [ ] APIs do not require methods to be called in a specific order.
- [ ] If sequencing is needed, enforce it with constructors, factories, or state machines.

---

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
# 📜 LeBlanc's Law: "Later Equals Never"
A list of things we’ve intentionally deferred (to avoid forgetting):
- [ ] _______________
- [ ] _______________
- [ ] _______________