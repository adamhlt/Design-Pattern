package component.output;

import model.Classroom;

public class Output
{
    private Generator _generator;

    public void setGenerator(Generator generator)
    {
        this._generator = generator;
    }

    public void execute(Classroom classroom)
    {
        this._generator.Generate(classroom);
    }
}
