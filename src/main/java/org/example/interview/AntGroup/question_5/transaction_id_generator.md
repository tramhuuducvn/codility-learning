# Transaction ID Generator:

## Problem:
- Provide a solution to generate the unique transaction id which must not be duplicated.
- Assume all solutions will works on microservices environment.

## Solution:
First of all, I want to mention that there are many solutions to this problem, but I would like to share my idea below:

- Step 1: Generate a UUID v4 (Random, 122 bits of randomness → extremely low collision probability).
- Step 2: Append a timestamp for more human-readable (to help with ordering or tracking).
- Step 3: Add a prefix to identify the system.
- Step 4: Check collision by using Bloom Filter.
    - If it says “not present” → 100% the transaction id is not in Set → accept and insert.
    - If it says “present” → assume collision → go to step 1 to generate a new one.

## Advantages
| Advantage                        | Explanation                                                         |
| -------------------------------- | ------------------------------------------------------------------- |
| ✅ 100% no duplicated transaction id    | UUIDs are designed to be globally unique without coordination & Bloom filter resolve collision     |
| ✅ Scalable                       | No need for central coordination, suitable for distributed systems. |
| ✅ Memory-efficient              | Bloom Filters require significantly less memory than storing the full data set, making them ideal for handling billions of entries.                                  |
| ✅ Sortable (when timestamp used) | You can roughly sort by creation time if needed.                    |
| ✅ Customizable format            | Add prefixes, shard IDs, or customer IDs.                           |

## Disadvantages
| Disadvantage                                  | Explanation                                                                                   |
| --------------------------------------------- | --------------------------------------------------------------------------------------------- |
| ❌ Long IDs                                    | UUIDs are 36 characters long; combined with timestamp, can be verbose.                        |
| ❌ Not human-friendly                          | UUIDs are hard to read, compare, or type.                                                     |
| ❌ Time ordering not perfect                   | Unless timestamp is first (or used exclusively), it’s not naturally ordered.                  |
| ❌ Some duplication risk in extreme edge cases | If only UUID v4 is used with bad random generators, very unlikely but theoretically possible. |
| ❌ Hard to implement | Apply Bloom Filter is a big challenge for many developers, it require a deep knowledge and mathematic skill. |