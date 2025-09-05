## 🤔 Self-Review Questions
- [ ] Is the code obvious to a reader?  
- [ ] Does it look like I cared enough?  
- [ ] Is the code communicating intent clearly?  
- [ ] Would another engineer find it easy to change/extend?  
- [ ] **Boy Scout Rule:** Did I leave the ground cleaner than I found it?  
	- Rename one variable, break one large function, eliminate duplication, simplify one composite condition, etc.

## 🔎 Attention to Detail (LLD)
- [ ] **Abbreviated error handling** → No empty catches, vague logs, or TODOs. Every error path must define recovery or fail-fast.  
- [ ] **Memory leaks** → All resources (DB connections, sockets, file handles, listeners) are closed or released.  
- [ ] **Race conditions** → Shared state is protected (atomic ops, locks, immutability) to avoid concurrent corruption.  
- [ ] **Inconsistent naming** → One concept = one name across modules. No synonyms (e.g., `customerId` vs `clientId`).  

## 📜 LeBlanc's Law: "Later Equals Never"
A list of things we’ve intentionally deferred (to avoid forgetting):
- [ ] _______________
- [ ] _______________
- [ ] _______________