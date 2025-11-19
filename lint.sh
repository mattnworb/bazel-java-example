#!/bin/bash
#
# does a bazel build with the linters activated
#
# this is a script just to simplify not having to remember all the flags to pass
set -euo pipefail
IFS=$'\n\t'

bazel build //... --aspects=//tools/lint:linters.bzl%buf --@aspect_rules_lint//lint:fail_on_violation
