public class CorUtil {
    //teste
    public static int adicionarLuzAmbiente(int corOriginal, double energiaLuzAmbiente) {
        //Extrair componentes RGB da cor original
        int r = (corOriginal >> 16) & 0xFF;
        int g = (corOriginal >> 8) & 0xFF;
        int b = corOriginal & 0xFF;

        //Aplicar a energia da luz ambiente em cada componente
        r = Math.min(255, (int) (r * energiaLuzAmbiente));
        g = Math.min(255, (int) (g * energiaLuzAmbiente));
        b = Math.min(255, (int) (b * energiaLuzAmbiente));

        //Recombinar os componentes em uma única cor
        return (r << 16) | (g << 8) | b;
    }

    public static int adicionarEnergiasLuz(int corOriginal, double energiaDifusa, double energiaEspecular) {
        //Extrair componentes RGB da cor original
        int r = (corOriginal >> 16) & 0xFF;
        int g = (corOriginal >> 8) & 0xFF;
        int b = corOriginal & 0xFF;

        //Calcular componentes difusos
        r = Math.min(255, (int) (r * energiaDifusa));
        g = Math.min(255, (int) (g * energiaDifusa));
        b = Math.min(255, (int) (b * energiaDifusa));

        //Adicionar componentes especulares
        r = Math.min(255, (int) (r + 255 * energiaEspecular));
        g = Math.min(255, (int) (g + 255 * energiaEspecular));
        b = Math.min(255, (int) (b + 255 * energiaEspecular));

        //Recombinar os componentes em uma única cor
        return (r << 16) | (g << 8) | b;
    }
}