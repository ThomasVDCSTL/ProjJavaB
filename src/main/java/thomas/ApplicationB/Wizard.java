package thomas.ApplicationB;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Classe enfant de Hero")
public class Wizard extends Hero{
    public Wizard(int id, String name) {
        super(id,name,"Magicien",8);
    }
}
