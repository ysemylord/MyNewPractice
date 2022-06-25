package base

/**
 * Person反编译过后
 *
    public final class Person {
     @NotNull
    private String name = "jack";

    @NotNull
    public final String getName() {
      return "dear " + this.name;
    }

   public final void setName(@NotNull String value) {
    this.name = StringsKt.trim((CharSequence)value).toString();
   }
   }
 */
class Person(){
    var name:String="jack"
              get() {
                  return "dear $field"
              }
              set(value) {
                  field = value.trim()
              }
}

class Base {


}