$(document).ready(function () {
    if ($('#studentsTable tbody').length) {
        $.ajax({
            url: "/getAllStudents",
            type: 'GET',
            success: function(data) {
                $('#studentsTable tbody').html(getStudentsContent(data));
            }
        });
    }

    $("#addStudentButton").click(function () {
        window.location = "/addStudent.html";
    });

    if ($('.addStudent').length) {
        $(".addStudent button#addStudent").click(function () {
            var object = {
                id: null,
                schoolYear: $(".addStudent input#schoolYear")[0].value,
                campus: $(".addStudent input#campus")[0].value,
                entryDate: new Date($(".addStudent input#entryDate")[0].value),
                gradeLevel: $(".addStudent input#gradeLevel")[0].value,
                name: $(".addStudent input#name")[0].value
            };
            $.ajax({
                url: "/saveStudent",
                type: 'POST',
                data: JSON.stringify(object),
                success: function (data) {
                    window.location = "/index.html";
                },
                contentType: "application/json"
            });
        });
    }

    if ($('.editStudent').length) {
        var getUrlParameter = function(sParam) {
            var sPageURL = window.location.search.substring(1),
                sURLVariables = sPageURL.split('&'),
                sParameterName,
                i;

            for (i = 0; i < sURLVariables.length; i++) {
                sParameterName = sURLVariables[i].split('=');

                if (sParameterName[0] === sParam) {
                    return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
                }
            }
        };

        var studentID = getUrlParameter("studentID");
        $.ajax({
            url: "/findStudentByID",
            type: 'GET',
            data: {
                id: studentID
            },
            success: function(data) {
                if (data !== null || data !== undefined) {
                    $(".editStudent input#name")[0].value = data.name;
                    $(".editStudent input#schoolYear")[0].value = data.schoolYear;
                    $(".editStudent input#campus")[0].value = data.campus;
                    $(".editStudent input#entryDate")[0].value = new Date(data.entryDate).toLocaleDateString(undefined, {
                        day: '2-digit',
                        month: '2-digit',
                        year: 'numeric'
                    });
                    $(".editStudent input#gradeLevel")[0].value = data.gradeLevel;
                    $(".editStudent input#id")[0].value = data.id;
                }
            }
        });
        $(".editStudent button#editStudent").click(function () {
            var object = {
                id: $(".editStudent input#id")[0].value,
                schoolYear: $(".editStudent input#schoolYear")[0].value,
                campus: $(".editStudent input#campus")[0].value,
                entryDate: new Date($(".editStudent input#entryDate")[0].value),
                gradeLevel: $(".editStudent input#gradeLevel")[0].value,
                name: $(".editStudent input#name")[0].value
            };
            $.ajax({
                url: "/saveStudent",
                type: 'POST',
                data: JSON.stringify(object),
                success: function (data) {
                    window.location = "/index.html";
                },
                contentType: "application/json"
            });
        });

    }

    $("#searchInput").keypress(function (e) {
        var key = e.which;
        if (key === 13) {
            handleSearch(this.value);
        }
    });

    $("#searchButton").click(function () {
        handleSearch($("#searchInput")[0].value);
    });

    $('body').on('click', 'img#editIcon', function() {
        var studentID = this.closest('tr').id;
        window.location = "/editStudent.html?studentID=" + studentID;
    });

    var getStudentsContent = function(students) {
        var content = '';
        students.forEach(function (item) {
            var row =
                "<tr id='"+ item.id + "'>" +
                "<td>" + item.name + "</td>" +
                "<td>" + item.schoolYear + "</td>" +
                "<td>" + item.campus + "</td>" +
                "<td >" + new Date(item.entryDate).toLocaleDateString(undefined, {
                    day: '2-digit',
                    month: '2-digit',
                    year: 'numeric'
                }) + "</td>" +
                "<td>" + item.gradeLevel + "</td>" +
                "<td><img id=\"editIcon\" src=\"img/edit.png\" alt=\"\" style=\"width:auto; height:auto;\"></td>" +
                "</tr>";
            content += row;
        });

        return content;
    };

    var handleSearch = function(value) {
        $.ajax({
            url: "/searchStudents",
            type: 'GET',
            data: {
                value: value
            },
            success: function(data) {
                $('#studentsTable tbody').html(getStudentsContent(data));
            }
        });
    };

    $("form#importCSV").submit(function(e) {
        e.preventDefault();
        var formData = new FormData($("#importCSV")[0]);
        $.ajax({
            url: "/importStudentsFromCSV",
            type: 'POST',
            data: formData,
            success: function (data) {
                var content = '';
                $.ajax({
                    url: "/getAllStudents",
                    type: 'GET',
                    success: function(data) {
                        $('#studentsTable tbody').html(getStudentsContent(data));
                    }
                });
            },
            cache: false,
            contentType: false,
            processData: false
        });
    });
});