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

# 📜 LeBlanc's Law: "Later Equals Never"
A list of things we’ve intentionally deferred (to avoid forgetting):
- [ ] _______________
- [ ] _______________
- [ ] _______________