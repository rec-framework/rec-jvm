out.println("hello world");

var rec = new Rec("src/test/resources");

out.println(rec.basePath);

out.println(rec.from("Person").where(function (it) {
    out.println(it.get("first name"))
}));

out.println(rec.from("Person").select(["first name", "last name"]).where(function(it) {
    out.println(it)
}));


rec.rule("Person", function(it) {
    out.println(it.get("first name"));
    return it.get("first name").length() > 3
});