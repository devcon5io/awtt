/*
 * Copyright 2015,2016 DevCon5 GmbH, info@devcon5.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

var assert = function (func) {
    if (!func()) {
        throw "assertion failed " + new Error().stack.split("\n")[2] + " calling \n " + func.toString();
    }
}

/*Setup*/

var subject = {
    field1: "value"
}

function mapFunction(value) {
    if (value === "value") {
        return "mapped";
    }
    return "notMapped"
}

function flatMapFunction(value) {
    if (value === "value") {
        return "mapped";
    }
    return "notMapped"
}

function filterFunction(value) {
    if (value === "value") {
        return true;
    }
}

/*Test*/

assert(function () {
    return Optional.of(subject.field1).orElse("alternative") == "value";
});
assert(function () {
    return Optional.of(subject.field2).orElse("alternative") == "alternative";
});
assert(function () {
    return Optional.of(subject.field1).isPresent();
});
assert(function () {
    return !Optional.of(subject.field2).isPresent();
});
assert(function () {
    return Optional.of(subject.field1).map(mapFunction).get() == "mapped";
});
assert(function () {
    return Optional.of("other").map(mapFunction).get() == "notMapped";
});
assert(function () {
    return Optional.of(subject.field2).map(mapFunction) == Optional.empty();
});
assert(function () {
    return Optional.of(subject.field1).flatMap(flatMapFunction) == "mapped";
});
assert(function () {
    return Optional.of("other").flatMap(flatMapFunction) == "notMapped";
});
assert(function () {
    return Optional.of(subject.field2).flatMap(flatMapFunction) == "notMapped";
});
assert(function () {
    return Optional.of(subject.field1).filter(filterFunction).get() == subject.field1;
});
assert(function () {
    return Optional.of("other").filter(filterFunction) == Optional.empty();
});
assert(function () {
    var opt = Optional.of(subject.field2);
    return opt.filter(filterFunction) == opt;
});
assert(function () {
    var present = "notPresent";
    Optional.of(subject.field1).ifPresent(function () {
        present = "isPresent";
    });
    return present == "isPresent";
});
assert(function () {
    var present = "notPresent";
    Optional.of(subject.field2).ifPresent(function () {
        present = "isPresent";
    });
    return present == "notPresent";
});
print("Test sucessful");

