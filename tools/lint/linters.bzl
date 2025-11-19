load("@aspect_rules_lint//lint:buf.bzl", "lint_buf_aspect")
# load("@aspect_rules_lint//lint:checkstyle.bzl", "lint_checkstyle_aspect")
# load("@aspect_rules_lint//lint:pmd.bzl", "lint_pmd_aspect")
# load("@aspect_rules_lint//lint:spotbugs.bzl", "lint_spotbugs_aspect")

buf = lint_buf_aspect(
    config = Label("//:buf.yaml"),
)

# pmd = lint_pmd_aspect(
#     binary = Label("//tools/lint:pmd"),
#     rulesets = [Label("//:pmd.xml")],
# )

# checkstyle = lint_checkstyle_aspect(
#     binary = Label("//tools/lint:checkstyle"),
#     # config = Label("//:checkstyle.xml"),
#     # data = [Label("//:checkstyle-suppressions.xml")],
# )

# spotbugs = lint_spotbugs_aspect(
#     binary = Label("//tools/lint:spotbugs"),
#     # exclude_filter = Label("//:spotbugs-exclude.xml"),
# )
