# Platform Index Formation Algorithms

The document records the rules for forming database table indexes of metadata objects
and their calculation algorithms in the MDClasses model. For each metadata object, rules are
provided; for each rule — a complete list of index fields, a condition from the platform
documentation, formation algorithms, and the status of inclusion in the model.

The document serves as a traceability checklist: rule → algorithm → calculator branch → reference object in tests.

_Rules source: 1C:Enterprise 8.3.27, methodological support for developers and administrators (its.1c.ru, "Database Table Indexes" section)._

## General Conventions

### Separators

For all object indexes, the first fields are common attributes that act as separators
for the object in `SEPARATE_INDEPENDENTLY` mode: common configuration attributes whose
"Data Separation" property is set to `SEPARATE_INDEPENDENTLY` and that this object
is part of (usage list). In the index composition, they are denoted as `[ORNR…]`.

Part of the documentation conditions describe the choice between a field of a specific separator (`ORNR1`)
and a field of the hash function of separator values (`ORRH`). The hash (`ORRH`) is a physical representation
in the RDBMS and is not constructed in the model: in all such cases, all
independent separators of the object are included in the index.

### Name Correspondence

| Documentation Alias | Model Field |
| --- | --- |
| Link | standard attribute "Link" |
| Code | standard attribute "Code" |
| Title | standard attribute "Title" |
| Number | standard attribute "Number" |
| Date | standard attribute "Date" |
| Parent | standard attribute "Parent" |
| IsGroup | standard attribute "IsGroup" |
| Owner | standard attribute "Owner" |
| Order | standard attribute "Order" |
| Predefined | standard attribute "Predefined" |
| PredefinedDataName | standard attribute "PredefinedDataName" |
| Period | standard attribute "Period" |
| Recorder | standard attribute "Recorder" |
| LineNumber | standard attribute "LineNumber" |
| RegistrationPeriod | standard attribute "RegistrationPeriod" |
| ActionPeriod | standard attribute "ActionPeriod" |
| Completed | standard attribute "Completed" |
| Started | standard attribute "Started" |
| Performed | standard attribute "Performed" |
| LeadTask | standard attribute "LeadTask" |
| BusinessProcess | standard attribute "BusinessProcess" |
| RoutePoint | standard attribute "RoutePoint" |
| Attribute | object attribute (attribute) |
| Dimension | register dimension |
| Resource | register resource |
| Column | document journal column |
| BaseDimensionN | calculation register dimension with "Base" property (`baseDimension`) |

### Legend

- ✅ — rule is included in the model;
- ❌ — rule is excluded from the model with the reason specified;
- `codeLength`, `descriptionLength`, `numberLength`, `orderLength` — corresponding object length properties;
- `mainPresentation` — "Main Presentation" property (`AS_CODE` / `AS_DESCRIPTION`);
- `indexing` — "Index" property of an attribute/column (`INDEX` / `INDEX_WITH_ADDITIONAL_ORDER`);
- `master` — "Master" property of a dimension;
- `foldersOnTop` — "Folders on Top" property;
- `writeMode` — information register write mode (`INDEPENDENT` / `RECORDER_SUBORDINATE`);
- `periodicity` — information register periodicity;
- `correspondence` — accounting register "Correspondence" property;
- `actionPeriod` — calculation register "Action Period" property.

---

## Catalog

### Main Indexes

#### Rule 1 — [ORNR1 + ... +] Link (Clustered)

Condition: Always. The index includes fields of independent separators that separate this catalog.

Algorithm 1.1: Create an index of composition [ORNR…] + Link, clustered.

Status: ✅

#### Rule 2 — [ORRH | ORNR1 +] Code + Link

Condition: The "Code Length" property is not equal to 0. If the catalog is separated by a single
separator whose type is not String, the index contains a field of this separator. If the type
of separator is String, or the separator is independent and shared, or there is more than one
separator, then the index contains a field of the hash function value of separator values. This rule
applies to all indexes where [ORRH | ORNR1 +] is specified in composition.

Algorithm 2.1: If `codeLength != 0` — create an index of composition [ORNR…] + Code + Link.
The choice between a separator field and hash (part of the condition about ORRH) is not modeled:
all independent separators of the object are included.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] Title + Link

Condition: The "Title Length" property is not equal to 0.

Algorithm 3.1: If `descriptionLength != 0` — create an index of composition [ORNR…] + Title + Link.

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Attribute + Link

Condition: For the "Attribute" attribute, the "Index" property is set to "Index".

Algorithm 4.N: For each catalog attribute with `indexing == INDEX` — create an index
of composition [ORNR…] + Attribute_N + Link.

Status: ✅

#### Rule 5 — [ORRH | ORNR1 +] Attribute + Code + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Code Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Code".

Algorithm 5.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `codeLength != 0 && mainPresentation == AS_CODE` — create an index of composition
[ORNR…] + Attribute_N + Code + Link.

Status: ✅

#### Rule 6 — [ORRH | ORNR1 +] Attribute + Title + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Title Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Title".

Algorithm 6.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `descriptionLength != 0 && mainPresentation == AS_DESCRIPTION` — create an index of composition
[ORNR…] + Attribute_N + Title + Link.

Status: ✅

#### Rule 7 — [ORRH | ORNR1 +] Attribute

Condition: The catalog is included in a selection criterion via the "Attribute" attribute.

Algorithm 7.N: For each attribute that is part of any configuration selection criterion
together with a link to this catalog — create an index of composition
[ORNR…] + Attribute_N.

Status: ✅

#### Rule 8 — [ORRH | ORNR1 +] PredefinedID

Condition: Index by identifier of a predefined metadata object.

Status: ❌ Not implemented.

### Additional indexes for subordinate catalog (regardless of catalog hierarchy)

Rules apply if the catalog is subordinate (owners list is not empty). Indexes
are added to the main ones.

#### Rule 9 — [ORRH | ORNR1 +] Owner + Link

Condition: The "Code Length" property is equal to 0.

Algorithm 9.1: If the catalog is subordinate and `codeLength == 0` — create an index of composition
[ORNR…] + Owner + Link.

Status: ✅

#### Rule 10 — [ORRH | ORNR1 +] Owner + Code + Link

Condition: The "Code Length" property is not equal to 0.

Algorithm 10.1: If the catalog is subordinate and `codeLength != 0` — create an index of composition
[ORNR…] + Owner + Code + Link.

Status: ✅

#### Rule 11 — [ORRH | ORNR1 +] Owner + Title + Link

Condition: The "Title Length" property is not equal to 0.

Algorithm 11.1: If the catalog is subordinate and `descriptionLength != 0` — create an index of composition
[ORNR…] + Owner + Title + Link.

Status: ✅

#### Rule 12 — [ORRH | ORNR1 +] Owner + Attribute + Link

Condition: For the "Attribute" attribute, the "Index" property is set to "Index".

Algorithm 12.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Owner + Attribute_N + Link.

Status: ✅

#### Rule 13 — [ORRH | ORNR1 +] Owner + Attribute + Code + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Code Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Code".

Algorithm 13.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `codeLength != 0 && mainPresentation == AS_CODE` — create an index of composition
[ORNR…] + Owner + Attribute_N + Code + Link.

Status: ✅

#### Rule 14 — [ORRH | ORNR1 +] Owner + Attribute + Title + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Title Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Title".

Algorithm 14.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `descriptionLength != 0 && mainPresentation == AS_DESCRIPTION` — create an index of composition
[ORNR…] + Owner + Attribute_N + Title + Link.

Status: ✅

### Additional indexes for hierarchical non-subordinate catalog

Rules apply if the catalog is hierarchical and not subordinate. If the
"Folders on Top" property (`foldersOnTop`) is set, alongside the Parent field the
IsGroup field is also involved in indexes; without folders on top the composition is the same, but the IsGroup field is not included.
Indexes are added to the main ones.

#### Rule 15 — [ORRH | ORNR1 +] Parent + IsGroup + Link

Condition: The "Code Length" property is equal to 0 and the "Title Length" property is equal to 0.

Algorithm 15.1: If the catalog is hierarchical non-subordinate, both lengths are 0
(`codeLength == 0 && descriptionLength == 0`) — create an index of composition
[ORNR…] + Parent [+ IsGroup at `foldersOnTop`] + Link.

Status: ✅

#### Rule 16 — [ORRH | ORNR1 +] Parent + IsGroup + Code + Link

Condition: The "Code Length" property is not equal to 0.

Algorithm 16.1: If the catalog is hierarchical non-subordinate and `codeLength != 0` — create
an index of composition [ORNR…] + Parent [+ IsGroup at `foldersOnTop`] + Code + Link.

Status: ✅

#### Rule 17 — [ORRH | ORNR1 +] Parent + IsGroup + Title + Link

Condition: The "Title Length" property is not equal to 0.

Algorithm 17.1: If the catalog is hierarchical non-subordinate and `descriptionLength != 0` —
create an index of composition [ORNR…] + Parent [+ IsGroup at `foldersOnTop`] + Title + Link.

Status: ✅

#### Rule 18 — [ORRH | ORNR1 +] Parent + IsGroup + Attribute + Link

Condition: For the "Attribute" attribute, the "Index" property is set to "Index".

Algorithm 18.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Parent [+ IsGroup at `foldersOnTop`] + Attribute_N + Link.

Status: ✅

#### Rule 19 — [ORRH | ORNR1 +] Parent + IsGroup + Attribute + Code + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Code Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Code".

Algorithm 19.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `codeLength != 0 && mainPresentation == AS_CODE` — create an index of composition
[ORNR…] + Parent [+ IsGroup at `foldersOnTop`] + Attribute_N + Code + Link.

Status: ✅

#### Rule 20 — [ORRH | ORNR1 +] Parent + IsGroup + Attribute + Title + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Title Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Title".

Algorithm 20.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `descriptionLength != 0 && mainPresentation == AS_DESCRIPTION` — create an index of composition
[ORNR…] + Parent [+ IsGroup at `foldersOnTop`] + Attribute_N + Title + Link.

Status: ✅

### Additional indexes for hierarchical subordinate catalog

Rules apply if the catalog is hierarchical and subordinate. The IsGroup field is involved
at `foldersOnTop`, just as for non-subordinate catalogs. Indexes are added to the main ones.

#### Rule 21 — [ORRH | ORNR1 +] Owner + Parent + IsGroup + Link

Condition: The "Code Length" property is equal to 0 and the "Title Length" property is equal to 0.

Algorithm 21.1: If the catalog is hierarchical subordinate, both lengths are 0 — create an index
of composition [ORNR…] + Owner + Parent [+ IsGroup at `foldersOnTop`] + Link.

Status: ✅

#### Rule 22 — [ORRH | ORNR1 +] Owner + Parent + IsGroup + Code + Link

Condition: The "Code Length" property is not equal to 0.

Algorithm 22.1: If the catalog is hierarchical subordinate and `codeLength != 0` — create an index
of composition [ORNR…] + Owner + Parent [+ IsGroup at `foldersOnTop`] + Code + Link.

Status: ✅

#### Rule 23 — [ORRH | ORNR1 +] Owner + Parent + IsGroup + Title + Link

Condition: The "Title Length" property is not equal to 0.

Algorithm 23.1: If the catalog is hierarchical subordinate and `descriptionLength != 0` — create
an index of composition [ORNR…] + Owner + Parent [+ IsGroup at `foldersOnTop`] + Title + Link.

Status: ✅

#### Rule 24 — [ORRH | ORNR1 +] Owner + Parent + IsGroup + Attribute + Link

Condition: For the "Attribute" attribute, the "Index" property is set to "Index".

Algorithm 24.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Owner + Parent [+ IsGroup at `foldersOnTop`] + Attribute_N + Link.

Status: ✅

#### Rule 25 — [ORRH | ORNR1 +] Owner + Parent + IsGroup + Attribute + Code + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Code Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Code".

Algorithm 25.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `codeLength != 0 && mainPresentation == AS_CODE` — create an index of composition
[ORNR…] + Owner + Parent [+ IsGroup at `foldersOnTop`] + Attribute_N + Code + Link.

Status: ✅

#### Rule 26 — [ORRH | ORNR1 +] Owner + Parent + IsGroup + Attribute + Title + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Title Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Title".

Algorithm 26.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `descriptionLength != 0 && mainPresentation == AS_DESCRIPTION` — create an index of composition
[ORNR…] + Owner + Parent [+ IsGroup at `foldersOnTop`] + Attribute_N + Title + Link.

Status: ✅

### Catalog options table

#### Rule 27 — [ORRH | ORNR1 +] Identifier (Clustered)

Condition: Always.

Status: ❌ Options table is excluded from the model by task decision.

---

## Document

#### Rule 1 — [ORNR1 + ... +] Link (Clustered)

Condition: Always. The index includes fields of independent separators that separate this document.

Algorithm 1.1: Create an index of composition [ORNR…] + Link, clustered.

Status: ✅

#### Rule 2 — [ORRH | ORNR1 +] Date + Link

Condition: Always.

Algorithm 2.1: Create an index of composition [ORNR…] + Date + Link.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] Number + Link

Condition: The "Number Length" property is not equal to 0.

Algorithm 3.1: If `numberLength != 0` — create an index of composition [ORNR…] + Number + Link.

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Attribute + Link

Condition: For the "Attribute" attribute, the "Index" property is set to "Index".

Algorithm 4.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Attribute_N + Link.

Status: ✅

#### Rule 5 — [ORRH | ORNR1 +] Attribute + Date + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering".

Algorithm 5.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER` — create
an index of composition [ORNR…] + Attribute_N + Date + Link (conditions on lengths and main
presentation, unlike catalogs, are absent).

Status: ✅

#### Rule 6 — [ORRH | ORNR1 +] Attribute

Condition: The document is included in a selection criterion via the "Attribute" attribute.

Algorithm 6.N: For each attribute that is part of any configuration selection criterion
together with a link to this document — create an index of composition [ORNR…] + Attribute_N.

Status: ✅

#### Rule 7 — [ORRH | ORNR1 +] NumberPrefix + Number + Link

Condition: The "Number Length" property is not equal to 0.

Status: ❌ Rule excluded — RDBMS implementation detail.

---

## Document Journal

#### Rule 1 — [ORRH | ORNR1 +] Link (Clustered)

Condition: Always.

Algorithm 1.1: Create an index of composition [ORNR…] + Link, clustered.

Status: ✅

#### Rule 2 — [ORRH | ORNR1 +] Date + Link

Condition: Always.

Algorithm 2.1: Create an index of composition [ORNR…] + Date + Link.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] Column + Link

Condition: For the journal "Column", the "Index" property is set to "Index".

Algorithm 3.N: For each journal column with `indexing == INDEX` — create an index of composition
[ORNR…] + Column_N + Link.

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Column + Date + Link

Condition: For the journal "Column", the "Index" property is set to
"Index with additional ordering".

Algorithm 4.N: For each journal column with `indexing == INDEX_WITH_ADDITIONAL_ORDER` — create
an index of composition [ORNR…] + Column_N + Date + Link.

Status: ✅

---

## Information Registry (of Characteristics)

The composition corresponds to the main indexes of a catalog with corrections: the code length and title length
of an information registry of characteristics cannot be zero; an information registry of characteristics
cannot be subordinate. Hierarchical indexes apply if the information registry of characteristics
is hierarchical.

#### Rule 1 — [ORNR1 + ... +] Link (Clustered)

Condition: Always.

Algorithm 1.1: Create an index of composition [ORNR…] + Link, clustered.

Status: ✅

#### Rule 2 — [ORRH | ORNR1 +] Code + Link

Condition: The "Code Length" property is not equal to 0 (for an information registry of characteristics, the code length is always non-zero).

Algorithm 2.1: Create an index of composition [ORNR…] + Code + Link.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] Title + Link

Condition: The "Title Length" property is not equal to 0 (for an information registry of characteristics, the title length is always
non-zero).

Algorithm 3.1: Create an index of composition [ORNR…] + Title + Link.

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Attribute + Link

Condition: For the "Attribute" attribute, the "Index" property is set to "Index".

Algorithm 4.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Attribute_N + Link.

Status: ✅

#### Rule 5 — [ORRH | ORNR1 +] Attribute + Code + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Code Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Code".

Algorithm 5.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `mainPresentation == AS_CODE` — create an index of composition
[ORNR…] + Attribute_N + Code + Link.

Status: ✅

#### Rule 6 — [ORRH | ORNR1 +] Attribute + Title + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Title Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Title".

Algorithm 6.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `mainPresentation == AS_DESCRIPTION` — create an index of composition
[ORNR…] + Attribute_N + Title + Link.

Status: ✅

#### Rule 7 — [ORRH | ORNR1 +] Attribute

Condition: The information registry of characteristics is included in a selection criterion via the "Attribute" attribute.

Algorithm 7.N: For each attribute that is part of any configuration selection criterion
together with a link to this information registry of characteristics — create an index of composition
[ORNR…] + Attribute_N.

Status: ✅

#### Rule 8 — [ORRH | ORNR1 +] PredefinedID

Condition: Index by identifier of a predefined metadata object.

Status: ❌ Rule not implemented.

#### Rule 9 — [ORRH | ORNR1 +] Parent + IsGroup + Code + Link

Condition: The "Code Length" property is not equal to 0. Applies to hierarchical information registry of characteristics.

Algorithm 9.1: If the information registry of characteristics is hierarchical — create
an index of composition [ORNR…] + Parent [+ IsGroup at `foldersOnTop`] + Code + Link.

Status: ✅

#### Rule 10 — [ORRH | ORNR1 +] Parent + IsGroup + Title + Link

Condition: The "Title Length" property is not equal to 0. Applies to hierarchical information registry of characteristics.

Algorithm 10.1: If the information registry of characteristics is hierarchical —
create an index of composition [ORNR…] + Parent [+ IsGroup at `foldersOnTop`] + Title + Link.

Status: ✅

#### Rule 11 — [ORRH | ORNR1 +] Parent + IsGroup + Attribute + Link

Condition: For the "Attribute" attribute, the "Index" property is set to "Index".
Applies to hierarchical information registry of characteristics.

Algorithm 11.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Parent [+ IsGroup at `foldersOnTop`] + Attribute_N + Link.

Status: ✅

#### Rule 12 — [ORRH | ORNR1 +] Parent + IsGroup + Attribute + Code + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Code Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Code". Applies to hierarchical
information registry of characteristics.

Algorithm 12.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `mainPresentation == AS_CODE` — create an index of composition
[ORNR…] + Parent [+ IsGroup at `foldersOnTop`] + Attribute_N + Code + Link.

Status: ✅

#### Rule 13 — [ORRH | ORNR1 +] Parent + IsGroup + Attribute + Title + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Title Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Title". Applies to
hierarchical information registry of characteristics.

Algorithm 13.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `mainPresentation == AS_DESCRIPTION` — create an index of composition
[ORNR…] + Parent [+ IsGroup at `foldersOnTop`] + Attribute_N + Title + Link.

Status: ✅

Note: The index "Parent + IsGroup + Link" (both lengths equal to 0) for an information registry
of characteristics is unreachable, as the code and title lengths are always non-zero.

### Information Registry of Characteristics options table

#### Rule 14 — Identifier

Condition: Always.

Status: ❌ Options table is excluded from the model by task decision.

---

## Chart of Accounts

#### Rule 1 — [ORNR1 + ... +] Link (Clustered)

Condition: Always. The index includes fields of independent separators that separate this chart of accounts.

Algorithm 1.1: Create an index of composition [ORNR…] + Link, clustered.

Status: ✅

#### Rule 2 — [ORRH | ORNR1 +] Code + Link

Condition: Always.

Algorithm 2.1: Create an index of composition [ORNR…] + Code + Link.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] Parent + Code + Link

Condition: Always (a chart of accounts is always hierarchical).

Algorithm 3.1: Create an index of composition [ORNR…] + Parent + Code + Link.

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Title + Link

Condition: Always.

Algorithm 4.1: Create an index of composition [ORNR…] + Title + Link.

Status: ✅

#### Rule 5 — [ORRH | ORNR1 +] Parent + Title + Link

Condition: Always.

Algorithm 5.1: Create an index of composition [ORNR…] + Parent + Title + Link.

Status: ✅

#### Rule 6 — [ORRH | ORNR1 +] Order + Link

Condition: The "Order Length" property is not equal to 0.

Algorithm 6.1: If `orderLength != 0` — create an index of composition [ORNR…] + Order + Link.

Status: ✅

#### Rule 7 — [ORRH | ORNR1 +] Parent + Order + Link

Condition: The "Order Length" property is not equal to 0.

Algorithm 7.1: If `orderLength != 0` — create an index of composition [ORNR…] + Parent + Order + Link.

Status: ✅

#### Rule 8 — [ORRH | ORNR1 +] Attribute + Link

Condition: For the "Attribute" attribute, the "Index" property is set to "Index".

Algorithm 8.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Attribute_N + Link.

Status: ✅

#### Rule 9 — [ORRH | ORNR1 +] Parent + Attribute + Link

Condition: For the "Attribute" attribute, the "Index" property is set to "Index".

Algorithm 9.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Parent + Attribute_N + Link.

Status: ✅

#### Rule 10 — [ORRH | ORNR1 +] Attribute + Order + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Order Length" property is not equal to 0.

Algorithm 10.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `orderLength != 0` — create an index of composition [ORNR…] + Attribute_N + Order + Link.

Status: ✅

#### Rule 11 — [ORRH | ORNR1 +] Parent + Attribute + Order + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Order Length" property is not equal to 0.

Algorithm 11.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `orderLength != 0` — create an index of composition [ORNR…] + Parent + Attribute_N + Order + Link.

Status: ✅

#### Rule 12 — [ORRH | ORNR1 +] Attribute + Code + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Order Length" property is equal to 0,
and the "Main Presentation" property is set to "As Code".

Algorithm 12.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `orderLength == 0 && mainPresentation == AS_CODE` — create an index of composition
[ORNR…] + Attribute_N + Code + Link.

Status: ✅

#### Rule 13 — [ORRH | ORNR1 +] Parent + Attribute + Code + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Order Length" property is equal to 0,
and the "Main Presentation" property is set to "As Code".

Algorithm 13.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `orderLength == 0 && mainPresentation == AS_CODE` — create an index of composition
[ORNR…] + Parent + Attribute_N + Code + Link.

Status: ✅

#### Rule 14 — [ORRH | ORNR1 +] Attribute + Title + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Order Length" property is equal to 0,
and the "Main Presentation" property is set to "As Title".

Algorithm 14.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `orderLength == 0 && mainPresentation == AS_DESCRIPTION` — create an index of composition
[ORNR…] + Attribute_N + Title + Link.

Status: ✅

#### Rule 15 — [ORRH | ORNR1 +] Parent + Attribute + Title + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Order Length" property is equal to 0,
and the "Main Presentation" property is set to "As Title".

Algorithm 15.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `orderLength == 0 && mainPresentation == AS_DESCRIPTION` — create an index of composition
[ORNR…] + Parent + Attribute_N + Title + Link.

Status: ✅

#### Rule 16 — [ORRH | ORNR1 +] Attribute

Condition: The chart of accounts is included in a selection criterion via the "Attribute" attribute.

Algorithm 16.N: For each attribute that is part of any configuration selection criterion
together with a link to this chart of accounts — create an index of composition [ORNR…] + Attribute_N.

Status: ✅

#### Rule 17 — [ORRH | ORNR1 +] PredefinedID

Condition: Index by identifier of a predefined metadata object.

Status: ❌ By task decision, the predefined index is not modeled.

### Chart of Accounts options table

#### Rule 18 — Identifier

Condition: Always.

Status: ❌ Options table is excluded from the model by task decision.

---

## Chart of Calculation Types

The composition corresponds to the main indexes of a catalog with the correction: the code length and title length
of a chart of calculation types cannot be zero. A chart of calculation types is neither
subordinate nor hierarchical.

#### Rule 1 — [ORNR1 + ... +] Link (Clustered)

Condition: Always.

Algorithm 1.1: Create an index of composition [ORNR…] + Link, clustered.

Status: ✅

#### Rule 2 — [ORRH | ORNR1 +] Code + Link

Condition: The "Code Length" property is not equal to 0 (for a chart of calculation types, the code length is always non-zero).

Algorithm 2.1: Create an index of composition [ORNR…] + Code + Link.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] Title + Link

Condition: The "Title Length" property is not equal to 0 (for a chart of calculation types, the title length is always
non-zero).

Algorithm 3.1: Create an index of composition [ORNR…] + Title + Link.

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Attribute + Link

Condition: For the "Attribute" attribute, the "Index" property is set to "Index".

Algorithm 4.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Attribute_N + Link.

Status: ✅

#### Rule 5 — [ORRH | ORNR1 +] Attribute + Code + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Code Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Code".

Algorithm 5.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `mainPresentation == AS_CODE` — create an index of composition
[ORNR…] + Attribute_N + Code + Link.

Status: ✅

#### Rule 6 — [ORRH | ORNR1 +] Attribute + Title + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Title Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Title".

Algorithm 6.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `mainPresentation == AS_DESCRIPTION` — create an index of composition
[ORNR…] + Attribute_N + Title + Link.

Status: ✅

#### Rule 7 — [ORRH | ORNR1 +] Attribute

Condition: The chart of calculation types is included in a selection criterion via the "Attribute" attribute.

Algorithm 7.N: For each attribute that is part of any configuration selection criterion
together with a link to this chart of calculation types — create an index of composition [ORNR…] + Attribute_N.

Status: ✅

#### Rule 8 — [ORRH | ORNR1 +] PredefinedID

Condition: Index by identifier of a predefined metadata object.

Status: ❌ By task decision, the predefined index is not modeled.

### Chart of Calculation Types options table

#### Rule 9 — Identifier

Condition: Always.

Status: ❌ Options table is excluded from the model by task decision.

---

## Exchange Plan

The composition corresponds to the main indexes of a catalog with the correction: the code length and title length
of an exchange plan cannot be zero. An exchange plan is neither subordinate
nor hierarchical.

#### Rule 1 — [ORNR1 + ... +] Link (Clustered)

Condition: Always.

Algorithm 1.1: Create an index of composition [ORNR…] + Link, clustered.

Status: ✅

#### Rule 2 — [ORRH | ORNR1 +] Code + Link

Condition: The "Code Length" property is not equal to 0 (for an exchange plan, the code length is always non-zero).

Algorithm 2.1: Create an index of composition [ORNR…] + Code + Link.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] Title + Link

Condition: The "Title Length" property is not equal to 0 (for an exchange plan, the title length is always
non-zero).

Algorithm 3.1: Create an index of composition [ORNR…] + Title + Link.

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Attribute + Link

Condition: For the "Attribute" attribute, the "Index" property is set to "Index".

Algorithm 4.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Attribute_N + Link.

Status: ✅

#### Rule 5 — [ORRH | ORNR1 +] Attribute + Code + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Code Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Code".

Algorithm 5.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `mainPresentation == AS_CODE` — create an index of composition
[ORNR…] + Attribute_N + Code + Link.

Status: ✅

#### Rule 6 — [ORRH | ORNR1 +] Attribute + Title + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering" and at the same time the "Title Length" property is not equal to 0,
and the "Main Presentation" property is set to "As Title".

Algorithm 6.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER`
at `mainPresentation == AS_DESCRIPTION` — create an index of composition
[ORNR…] + Attribute_N + Title + Link.

Status: ✅

#### Rule 7 — [ORRH | ORNR1 +] Attribute

Condition: The exchange plan is included in a selection criterion via the "Attribute" attribute.

Algorithm 7.N: For each attribute that is part of any configuration selection criterion
together with a link to this exchange plan — create an index of composition [ORNR…] + Attribute_N.

Status: ✅

#### Rule 8 — [ORRH | ORNR1 +] PredefinedID

Condition: Index by identifier of a predefined metadata object.

Status: ❌ Rule excluded.

---

## Tabular Section

Rules apply to all tables that provide access to tabular sections of objects.

#### Rule 1 — [ORNR1 + ... +] Link + Key (Clustered)

Condition: Always. The index includes fields of independent separators that separate the object
to which the tabular section belongs.

Status: ❌ Rule excluded — RDBMS implementation detail.

#### Rule 2 — [ORRH | ORNR1 +] Attribute + Link

Condition: The configuration object is included in a selection criterion via the "Attribute" attribute of the tabular
section or the tabular section attribute has the "Index" property set.

Algorithm 2.N: For each tabular section attribute that has `indexing == INDEX` or
that is part of any configuration selection criterion together with a link to
the owner object — create an index of composition [ORNR…] + Attribute_N + Link.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] PredefinedID

Condition: Index by identifier of a predefined metadata object.

Status: ❌ Rule excluded.

---

## Information Register

### Non-periodic Information Register

#### Rule 1 — [ORRH | ORNR1 +] Dimension1 + [Dimension2 + ...] (Clustered)

Condition: There is at least one register dimension. An index including all dimensions in the order
in which they are defined during configuration. The index is clustered if the register is independent.

Algorithm 1.1: If the register has at least one dimension — create an index of composition
[ORNR…] + Dimension1 + ... + DimensionM (all dimensions in order of definition).
Clustered ⇔ `writeMode == INDEPENDENT`.

Status: ✅

#### Rule 2 — [ORRH | ORNR1 +] DimensionN + Dimension1 + [Dimension2 + ...]

Condition: The "DimensionN" dimension has the "Index" property set or "Master" property
and at the same time it is not the first and not the only dimension. An index including all dimensions.
The first field is DimensionN, then all other dimensions in the order in which they are defined
during configuration.

Algorithm 2.N: For each dimension N, except the first and only one, with
`indexing == INDEX || master` — create an index of composition [ORNR…] + DimensionN +
Dimension1 + ... (other dimensions in order of definition).

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] Attribute + Dimension1 + [Dimension2 + ...]

Condition: The "Attribute" attribute has the "Index" property set. An index in which the first field is
Attribute, then all dimensions in the order in which they are defined during configuration.

Algorithm 3.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Attribute_N + Dimension1 + ... (all dimensions in order of definition).

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Resource + Dimension1 + [Dimension2 + ...]

Condition: The "Resource" resource has the "Index" property set. An index in which the first field is
Resource, then all dimensions in the order in which they are defined during configuration.

Algorithm 4.N: For each resource with `indexing == INDEX` — create an index of composition
[ORNR…] + Resource_N + Dimension1 + ... (all dimensions in order of definition).

Status: ✅

### Periodic Information Register

#### Rule 1 — [ORRH | ORNR1 +] Period + [Dimension1 + ...]

Condition: Always.

Algorithm 1.1: Create an index of composition [ORNR…] + Period + Dimension1 + ... (all dimensions
in order of definition).

Status: ✅

#### Rule 2 — [ORRH | ORNR1 +] Dimension1 + [Dimension2 + ...] + Period (Clustered)

Condition: There is at least one register dimension. An index including all dimensions in the order
in which they are defined during configuration and the Period field.

Algorithm 2.1: If the register has at least one dimension — create an index of composition
[ORNR…] + Dimension1 + ... + DimensionM + Period. Interpretation: clustered ⇔
`writeMode == INDEPENDENT` (by analogy with a non-periodic register).

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] DimensionN + Period + Dimension1 + [Dimension2 + ...]

Condition: The "DimensionN" dimension has the "Index" property set or "Master" property
and at the same time it is not the only dimension. An index including the Period field and all dimensions.
The first field is DimensionN, then the Period field, then all other dimensions in the order
in which they are defined during configuration.

Algorithm 3.N: For each dimension N, except the only one, with `indexing == INDEX || master` —
create an index of composition [ORNR…] + DimensionN + Period + Dimension1 + ... (other dimensions
in order of definition). Unlike a non-periodic register, the first dimension also
participates (if it is not the only one).

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Attribute + Period + [Dimension1 + ...]

Condition: The "Attribute" attribute has the "Index" property set. An index in which the first field is
Attribute, then the Period field, then all dimensions in the order in which they are defined
during configuration.

Algorithm 4.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Attribute_N + Period + Dimension1 + ...

Status: ✅

#### Rule 5 — [ORRH | ORNR1 +] Resource + Period + [Dimension1 + ...]

Condition: The "Resource" resource has the "Index" property set. An index in which the first field is
Resource, then the Period field, then all dimensions in the order in which they are defined
during configuration.

Algorithm 5.N: For each resource with `indexing == INDEX` — create an index of composition
[ORNR…] + Resource_N + Period + Dimension1 + ...

Status: ✅

### Additional index for information register subordinate to recorder

Rules apply at `writeMode == RECORDER_SUBORDINATE`.

#### Rule 1 — [ORNR1 + ... +] Recorder + LineNumber (Clustered)

Condition: Always. The index includes fields of independent separators that separate this register.
The index is clustered if the register is non-periodic.

Algorithm 1.1: Create an index of composition [ORNR…] + Recorder + LineNumber.
Clustered ⇔ register is non-periodic.

Status: ✅

### Information Register with periodicity "At recorder position"

Rules apply at `periodicity == RECORDER_POSITION`; the set replaces the normal indexes
of a periodic information register (interpretation).

#### Rule 1 — [ORRH | ORNR1 +] Period + Recorder + LineNumber

Condition: Always.

Algorithm 1.1: Create an index of composition [ORNR…] + Period + Recorder + LineNumber.

Status: ✅

#### Rule 2 — [ORNR1 + ... +] Recorder + LineNumber

Condition: Always. The index includes fields of independent separators that separate this register.

Algorithm 2.1: Create an index of composition [ORNR…] + Recorder + LineNumber.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] Dimension1 + [Dimension2 + ...] + Period + Recorder + LineNumber (Clustered)

Condition: There is at least one register dimension. An index including all dimensions in the order
in which they are defined during configuration, the Period field and the Recorder field.

Algorithm 3.1: If the register has at least one dimension — create an index of composition
[ORNR…] + Dimension1 + ... + DimensionM + Period + Recorder + LineNumber, clustered.

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Dimension + Period + Recorder + LineNumber

Condition: The "Dimension" attribute has the "Index" property set.

Algorithm 4.N: For each dimension with `indexing == INDEX` (any, including the first) — create
an index of composition [ORNR…] + Dimension_N + Period + Recorder + LineNumber.

Status: ✅

#### Rule 5 — [ORRH | ORNR1 +] Attribute + Period + Recorder + LineNumber

Condition: The "Attribute" attribute has the "Index" property set.

Algorithm 5.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Attribute_N + Period + Recorder + LineNumber.

Status: ✅

#### Rule 6 — [ORRH | ORNR1 +] Resource + Period + Recorder + LineNumber

Condition: The "Resource" resource has the "Index" property set.

Algorithm 6.N: For each resource with `indexing == INDEX` — create an index of composition
[ORNR…] + Resource_N + Period + Recorder + LineNumber.

Status: ✅

### CutOffResultsFirst

#### Rule 1 — [ORRH | ORNR1 +] Dimension1 + [Dimension2 + ...] + Period (Clustered)

Condition: There is at least one register dimension. An index including all dimensions in the order
in which they are defined during configuration.

Status: ❌ Cut-off results table is service-level, excluded from the model by task decision.

#### Rule 2 — [ORRH | ORNR1 +] Attribute + [Dimension1 + ...]

Condition: The "Attribute" attribute has the "Index" property set.

Status: ❌ Cut-off results table is service-level, excluded from the model by task decision.

#### Rule 3 — [ORRH | ORNR1 +] Resource + [Dimension1 + ...]

Condition: The "Resource" resource has the "Index" property set.

Status: ❌ Cut-off results table is service-level, excluded from the model by task decision.

#### Rule 4 — [ORRH | ORNR1 +] DimensionN + Dimension1 + [Dimension2 + ...]

Condition: The "DimensionN" dimension has the "Index" property set or "Master" property
and at the same time it is not the only dimension. An index including all dimensions. The first field is
DimensionN, then all other dimensions in the order in which they are defined
during configuration.

Status: ❌ Cut-off results table is service-level, excluded from the model by task decision.

### CutOffResultsLast

#### Rule 1 — [ORRH | ORNR1 +] Dimension1 + [Dimension2 + ...] + Period (Clustered)

Condition: There is at least one register dimension. An index including all dimensions in the order
in which they are defined during configuration.

Status: ❌ Cut-off results table is service-level, excluded from the model by task decision.

#### Rule 2 — [ORRH | ORNR1 +] Attribute + [Dimension1 + ...]

Condition: The "Attribute" attribute has the "Index" property set.

Status: ❌ Cut-off results table is service-level, excluded from the model by task decision.

#### Rule 3 — [ORRH | ORNR1 +] Resource + [Dimension1 + ...]

Condition: The "Resource" resource has the "Index" property set.

Status: ❌ Cut-off results table is service-level, excluded from the model by task decision.

#### Rule 4 — [ORRH | ORNR1 +] DimensionN + Dimension1 + [Dimension2 + ...]

Condition: The "DimensionN" dimension has the "Index" property set or "Master" property
and at the same time it is not the only dimension. An index including all dimensions. The first field is
DimensionN, then all other dimensions in the order in which they are defined
during configuration.

Status: ❌ Cut-off results table is service-level, excluded from the model by task decision.

---

## Accumulation Register

### Main register table

The "Register Type" property (`registerType`) does not affect the composition of indexes of the main table.

#### Rule 1 — [ORRH | ORNR1 +] Period + Recorder + LineNumber (Clustered)

Condition: Always.

Algorithm 1.1: Create an index of composition [ORNR…] + Period + Recorder + LineNumber, clustered.

Status: ✅

#### Rule 2 — [ORNR1 + ... +] Recorder + LineNumber

Condition: Always. The index includes fields of independent separators that separate this register.

Algorithm 2.1: Create an index of composition [ORNR…] + Recorder + LineNumber.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] Dimension + Period + Recorder + LineNumber

Condition: The "Dimension" attribute has the "Index" property set.

Algorithm 3.N: For each dimension with `indexing == INDEX` — create an index of composition
[ORNR…] + Dimension_N + Period + Recorder + LineNumber.

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Attribute + Period + Recorder + LineNumber

Condition: The "Attribute" attribute has the "Index" property set.

Algorithm 4.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Attribute_N + Period + Recorder + LineNumber.

Status: ✅

---

## Accounting Register

### Main register table without correspondence

Rules apply at `correspondence == false`.

#### Rule 1 — [ORRH | ORNR1 +] Period + Recorder + LineNumber (Clustered)

Condition: Always.

Algorithm 1.1: Create an index of composition [ORNR…] + Period + Recorder + LineNumber, clustered.

Status: ✅

#### Rule 2 — [ORNR1 + ... +] Recorder + LineNumber

Condition: Always. The index includes fields of independent separators that separate this register.

Algorithm 2.1: Create an index of composition [ORNR…] + Recorder + LineNumber.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] Account + Period + Recorder

Condition: The register has a chart of accounts assigned.

Algorithm 3.1: Create an index of composition [ORNR…] + Account + Period + Recorder. For an
accounting register a chart of accounts is mandatory, therefore the index is always created.

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Dimension + Period + Recorder + LineNumber

Condition: The "Dimension" attribute has the "Index" property set.

Algorithm 4.N: For each dimension with `indexing == INDEX` — create an index of composition
[ORNR…] + Dimension_N + Period + Recorder + LineNumber.

Status: ✅

#### Rule 5 — [ORRH | ORNR1 +] Attribute + Period + Recorder + LineNumber

Condition: The "Attribute" attribute has the "Index" property set.

Algorithm 5.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Attribute_N + Period + Recorder + LineNumber.

Status: ✅

### Main register table with correspondence

Rules apply at `correspondence == true`. The composition of indexes differs from a register without
correspondence by the fact that instead of a single index by account, two indexes
by debit account and credit account are created; the remaining rules (1, 2, 4, 5) apply unchanged.

#### Rule 3D — [ORRH | ORNR1 +] AccountD + Period + Recorder

Condition: The register has a chart of accounts assigned.

Status: ❌ Rule excluded — virtual fields AccountD/AccountC are not stored in the model.

#### Rule 3K — [ORRH | ORNR1 +] AccountC + Period + Recorder

Condition: The register has a chart of accounts assigned.

Status: ❌ Rule excluded — virtual fields AccountD/AccountC are not stored in the model.

---

## Calculation Register

### Main calculation register table

#### Rule 1 — [ORRH | ORNR1 +] RegistrationPeriod + Recorder + LineNumber

Condition: Always.

Algorithm 1.1: Create an index of composition [ORNR…] + RegistrationPeriod + Recorder + LineNumber.

Status: ✅

#### Rule 2 — [ORNR1 + ... +] Recorder + LineNumber

Condition: Always. The index includes fields of independent separators that separate this register.

Algorithm 2.1: Create an index of composition [ORNR…] + Recorder + LineNumber.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] RegistrationPeriod + [BaseDimension1 + ...]

Condition: Always. An index by the RegistrationPeriod field and all base dimensions, i.e., by those
dimensions that have the "Base" property set. Base dimensions follow in the
order in which they are defined during configuration.

Algorithm 3.1: Create an index of composition [ORNR…] + RegistrationPeriod + BaseDimension1 + ...
(base dimensions — dimensions with `baseDimension == true` — in order of definition).

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] [BaseDimension1 + ...] + RegistrationPeriod

Condition: There is at least one base dimension. An index by all base dimensions, i.e., by those
dimensions that have the "Base" property set and the RegistrationPeriod field.

Algorithm 4.1: If there is at least one dimension with `baseDimension == true` — create an index
of composition [ORNR…] + BaseDimension1 + ... + RegistrationPeriod.

Status: ✅

#### Rule 5 — [ORRH | ORNR1 +] ActionPeriod + [BaseDimension1 + ...]

Condition: The calculation register "ActionPeriod" property is set. An index by the
ActionPeriod field and all base dimensions, i.e., by those dimensions that have the
"Base" property set. Base dimensions follow in the order in which they are defined
during configuration.

Algorithm 5.1: If `actionPeriod == true` — create an index of composition [ORNR…] + ActionPeriod +
BaseDimension1 + ...

Status: ✅

#### Rule 6 — [ORRH | ORNR1 +] [BaseDimension1 + ...] + ActionPeriod

Condition: The calculation register "ActionPeriod" property is set and there is at least one base
dimension. An index by all base dimensions, i.e., by those dimensions that have the
"Base" property set and the RegistrationPeriod field.

Algorithm 6.1: If `actionPeriod == true` and there is at least one dimension with
`baseDimension == true` — create an index of composition [ORNR…] + BaseDimension1 + ... + ActionPeriod.

Status: ✅

#### Rule 7 — [ORRH | ORNR1 +] Dimension + RegistrationPeriod + Recorder + LineNumber

Condition: The "Dimension" attribute has the "Index" property set.

Algorithm 7.N: For each dimension with `indexing == INDEX` — create an index of composition
[ORNR…] + Dimension_N + RegistrationPeriod + Recorder + LineNumber.

Status: ✅

#### Rule 8 — [ORRH | ORNR1 +] Attribute + RegistrationPeriod + Recorder + LineNumber

Condition: The "Attribute" attribute has the "Index" property set.

Algorithm 8.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Attribute_N + RegistrationPeriod + Recorder + LineNumber.

Status: ✅

---

## Sequences

### Main sequence table

#### Rule 1 — [ORRH | ORNR1 +] Recorder

Condition: Always.

Algorithm 1.1: Create an index of composition [ORNR…] + Recorder.

Status: ✅

#### Rule 2 — [ORRH | ORNR1 +] [Dimension1 + ...] + Period + Recorder

Condition: Always. An index by all dimension fields, the Period field and the Recorder field. Dimension
fields follow in the order in which they are defined during configuration.

Algorithm 2.1: Create an index of composition [ORNR…] + Dimension1 + ... + DimensionM + Period +
Recorder (dimensions in order of definition; if dimensions are absent — [ORNR…] + Period +
Recorder).

Status: ✅

---

## Enums

#### Rule 1 — [ORRH | ORNR1 +] Order + Link

Condition: Always. The Order field of enum tables corresponds to the order in which enum values
are located in the configuration tree.

Algorithm 1.1: Create an index of composition [ORNR…] + Order + Link.

Status: ✅

#### Rule 2 — [ORNR1 + ... +] Link (Clustered)

Condition: Always. The index includes fields of independent separators that separate this enum.

Algorithm 2.1: Create an index of composition [ORNR…] + Link, clustered.

Status: ✅

---

## Business Processes

### Main business process table

#### Rule 1 — [ORNR1 + ... +] Link (Clustered)

Condition: Always. The index includes fields of independent separators that separate this business process.

Algorithm 1.1: Create an index of composition [ORNR…] + Link, clustered.

Status: ✅

#### Rule 2 — [ORRH | ORNR1 +] Date + Link

Condition: Always.

Algorithm 2.1: Create an index of composition [ORNR…] + Date + Link.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] Number + Link

Condition: The "Number Length" property is not equal to 0.

Algorithm 3.1: If `numberLength != 0` — create an index of composition [ORNR…] + Number + Link.

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Completed + Date + Link

Condition: Always.

Algorithm 4.1: Create an index of composition [ORNR…] + Completed + Date + Link.

Status: ✅

#### Rule 5 — [ORRH | ORNR1 +] Started + Date + Link

Condition: Always.

Algorithm 5.1: Create an index of composition [ORNR…] + Started + Date + Link.

Status: ✅

#### Rule 6 — [ORRH | ORNR1 +] LeadTask + Link

Condition: Always.

Algorithm 6.1: Create an index of composition [ORNR…] + LeadTask + Link.

Status: ✅

#### Rule 7 — [ORRH | ORNR1 +] Attribute + Link

Condition: For the "Attribute" attribute, the "Index" property is set to "Index".

Algorithm 7.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Attribute_N + Link.

Status: ✅

#### Rule 8 — [ORRH | ORNR1 +] Attribute + Date + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering".

Algorithm 8.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER` — create
an index of composition [ORNR…] + Attribute_N + Date + Link.

Status: ✅

#### Rule 9 — [ORRH | ORNR1 +] Attribute

Condition: The business process is included in a selection criterion via the "Attribute" attribute.

Algorithm 9.N: For each attribute that is part of any configuration selection criterion
together with a link to this business process — create an index of composition [ORNR…] + Attribute_N.

Status: ✅

### Business process route points table

#### Rule 10 — [ORNR1 + ... +] Link (Clustered)

Condition: Always.

Status: ❌ Route points table is service-level, excluded from the model by task decision.

#### Rule 11 — [ORRH | ORNR1 +] Order + Link

Condition: Always. The Order field of route points tables corresponds to the order in which route points
were added to the graphical diagram of the business process.

Status: ❌ Route points table is service-level, excluded from the model by task decision.

---

## Tasks

#### Rule 1 — [ORNR1 + ... +] Link (Clustered)

Condition: Always. The index includes fields of independent separators that separate this task.

Algorithm 1.1: Create an index of composition [ORNR…] + Link, clustered.

Status: ✅

#### Rule 2 — [ORRH | ORNR1 +] Date + Link

Condition: Always.

Algorithm 2.1: Create an index of composition [ORNR…] + Date + Link.

Status: ✅

#### Rule 3 — [ORRH | ORNR1 +] Number + Link

Condition: The "Number Length" property is not equal to 0.

Algorithm 3.1: If `numberLength != 0` — create an index of composition [ORNR…] + Number + Link.

Status: ✅

#### Rule 4 — [ORRH | ORNR1 +] Title + Link

Condition: Always.

Algorithm 4.1: Create an index of composition [ORNR…] + Title + Link.

Status: ✅

#### Rule 5 — [ORRH | ORNR1 +] Performed + Title + Link

Condition: Always.

Algorithm 5.1: Create an index of composition [ORNR…] + Performed + Title + Link.

Status: ✅

#### Rule 6 — [ORRH | ORNR1 +] Performed + Date + Link

Condition: Always.

Algorithm 6.1: Create an index of composition [ORNR…] + Performed + Date + Link.

Status: ✅

#### Rule 7 — [ORRH | ORNR1 +] BusinessProcess + RoutePoint + Link

Condition: Always.

Algorithm 7.1: Create an index of composition [ORNR…] + BusinessProcess + RoutePoint + Link.

Status: ✅

#### Rule 8 — [ORRH | ORNR1 +] Performed + BusinessProcess + RoutePoint + Link

Condition: Always.

Algorithm 8.1: Create an index of composition [ORNR…] + Performed + BusinessProcess + RoutePoint + Link.

Status: ✅

#### Rule 9 — [ORRH | ORNR1 +] BusinessProcess + Date + Link

Condition: Always.

Algorithm 9.1: Create an index of composition [ORNR…] + BusinessProcess + Date + Link.

Status: ✅

#### Rule 10 — [ORRH | ORNR1 +] Attribute + Link

Condition: For the "Attribute" attribute, the "Index" property is set to "Index".

Algorithm 10.N: For each attribute with `indexing == INDEX` — create an index of composition
[ORNR…] + Attribute_N + Link.

Status: ✅

#### Rule 11 — [ORRH | ORNR1 +] Attribute + Date + Link

Condition: For the "Attribute" attribute, the "Index" property is set to
"Index with additional ordering".

Algorithm 11.N: For each attribute with `indexing == INDEX_WITH_ADDITIONAL_ORDER` — create
an index of composition [ORNR…] + Attribute_N + Date + Link.

Status: ✅

#### Rule 12 — [ORRH | ORNR1 +] Attribute

Condition: The task is included in a selection criterion via the "Attribute" attribute.

Algorithm 12.N: For each attribute that is part of any configuration selection criterion
together with a link to this task — create an index of composition [ORNR…] + Attribute_N.

Status: ✅

---

## Excluded indexes (summary)

The following groups of documentation indexes are not constructed in the model:

| Group | Reason |
| --- | --- |
| PredefinedID indexes (all objects) | By task decision, the predefined index is not modeled |
| Options tables of all objects ("Identifier") | Service tables are excluded |
| Accumulation register: balance and turnover tables, aggregates (aggregates, statistics, network aggregate options, new turnovers buffer, new turnovers, measurement codes, network of aggregates) | Service tables are excluded |
| Information register: cut-off results tables (CutOffResultsFirst, CutOffResultsLast) | Service tables are excluded |
| Accounting register: tables for totals by account and between accounts, superaccount table | Service tables are excluded |
| Calculation register: recalculation table, table of actual action periods | Service tables are excluded |
| Sequences: sequence boundary table | Service tables are excluded |
| Business processes: route points table | Service tables are excluded |
| Integration services, data history | Service tables, out of task scope |
| ORRH fields (separator hash), DimHash, Splitter, HashOfDimensions, SummarySeparator | Physical representation of indexes in the RDBMS, not constructed in the model |

## Documented Interpretations

1. Separator hash (ORRH): in all indexes, instead of choosing "separator field or hash",
   all independent separators of the object are included.
2. Clusteredness of an index by dimensions of a periodic information register — by analogy
   with a non-periodic one: clustered ⇔ register is independent.
3. The set of indexes of an information register with periodicity "At recorder position" replaces
   the normal set of a periodic register.
4. A chart of accounts for an accounting register is mandatory, therefore an index by account (Account) is always created.
