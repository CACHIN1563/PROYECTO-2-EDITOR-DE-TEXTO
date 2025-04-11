/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto.pkg2;

/**
 *
 * @author cachi
 */
import javax.swing.*;
import java.awt.*; // FUENTES, BORDES, TABULACIONES, ETC...
import java.awt.event.*;
import java.util.List;
import javax.swing.text.*;
import java.awt.print.PrinterException; // LIBRERIA NOS AYUDA A IMPRIMIR
import javax.swing.filechooser.FileNameExtensionFilter; // LIBRERIA NOS AYUDA A GUARDAR
import java.io.*; // PARA GUARDAR CORRECTAMENTE EL ARCHIVO

public class EDITORDETEXTO extends JFrame {
    private JTextArea areaTexto;
    private JButton btnDeshacer, btnRehacer, btnBuscar, btnReemplazar;
    private HISTORIAL historial;
    private String ultimaPalabra = "";

    public EDITORDETEXTO() {//VENTANA INICIAL
        super("EDITOR DE TEXTO PROYECTO II");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 400);
        setLayout(new BorderLayout());

        areaTexto = new JTextArea(); 
        areaTexto.setMargin
        (new Insets(10,  20,  10,  10)); // TABULACIÓN PARA QUE NO SALGA PEGADO EN LA IZQUIERDA
        JScrollPane scroll = 
                new JScrollPane(areaTexto);
        add(scroll, BorderLayout.CENTER);

        historial = new HISTORIAL();

        areaTexto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char 
                        c = e.getKeyChar();
                if 
                        (Character.isWhitespace(c) || c == '.' || c == ',') {
                    if 
                            (!ultimaPalabra.isEmpty()) {
                            historial.guardar(areaTexto.getText());
                            ultimaPalabra = "";
                    }
                } else {
                    ultimaPalabra += c;
                }
            }
        });

        JPanel panelIzquierdo = 
                new JPanel(new GridLayout(4, 1, 5, 5));
        btnDeshacer = 
                new JButton("DESHACER ⭯");
        btnRehacer =
                new JButton("REHACER ↻");  //BUSCAMOS EMOGIS EN https://emojigraph.org/es/card-index-dividers/
        btnBuscar = 
                new JButton("BUSCAR 🔎");
        btnReemplazar = 
                new JButton("REEMPLAZAR 🔄");

        panelIzquierdo.add(btnDeshacer);
        panelIzquierdo.add(btnRehacer);
        panelIzquierdo.add(btnBuscar);
        panelIzquierdo.add(btnReemplazar);
        add(panelIzquierdo, BorderLayout.WEST);

        btnDeshacer.addActionListener(e -> {
            String estado = historial.deshacer();
            if (estado != null) areaTexto.setText(estado);
        });

        btnRehacer.addActionListener(e -> { 
            String estado = historial.rehacer();
            if (estado != null) areaTexto.setText(estado);
        });

        btnBuscar.addActionListener(e -> buscarPalabra());

        btnReemplazar.addActionListener(e -> {
            String buscar = 
                    JOptionPane.showInputDialog
                                ("BUSCAR PALABRA");
            String reemplazo = 
                    JOptionPane.showInputDialog
                                ("REMPLAZAR PALABRA:");
            if 
                (buscar != null && reemplazo != null && !buscar.isEmpty()) {
                historial.guardar(areaTexto.getText());
                String nuevoTexto = BUSCADOR.reemplazar(areaTexto.getText(), buscar, reemplazo, true);
                areaTexto.setText(nuevoTexto);
            }
        });

        crearMenu();
        crearAtajos();
        setVisible(true);
    }

    private void crearMenu() { //EL MENÚ
        JMenuBar menuBar = 
                new JMenuBar();

        JMenu menuArchivo = new JMenu("ARCHIVO 🗂️"); //BUSCAMOS EMOGIS EN https://emojigraph.org/es/card-index-dividers/
            JMenuItem guardar = 
                new JMenuItem("GUARDAR COMO 💾");
            JMenuItem imprimir = 
                new JMenuItem("IMPRIMIR 🖨️");
            JMenuItem salir =
                new JMenuItem("SALIR ❎");

        guardar.addActionListener(e -> guardarArchivo());
        imprimir.addActionListener(e -> {
            try {
                areaTexto.print(); // SE REALIZA CON LIBRERIA PARA IMPRIMIR
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        });
        salir.addActionListener(e -> System.exit(0));

        menuArchivo.add(guardar);
        menuArchivo.add(imprimir);
        menuArchivo.add(salir);

        JMenu menuEditar = new JMenu("EDITAR 🖍️");
                JMenuItem colorFondo = 
                        new JMenuItem("COLOR DE FONDO");
                JMenuItem colorLetra = 
                        new JMenuItem("COLOR DE LETRA");
                JMenuItem tamañoLetra = 
                        new JMenuItem("TAMAÑO DE LETRA ");

                //PARA CAMBIAR COLORES
        colorFondo.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null, "SELECCIONE COOLOR DE FONDO", areaTexto.getBackground());
            if 
                    (color != null) areaTexto.setBackground(color);
        });

        colorLetra.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null, "SELECCIONE COLOR DE LETRA", areaTexto.getForeground());
            if 
                    (color != null) areaTexto.setForeground(color);
        });

        tamañoLetra.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("COLOQUE TAMAÑO DE LETRA:");
            try {
                int 
                        tamaño = Integer.parseInt(input);
                areaTexto.setFont(new Font(areaTexto.getFont().getName(), Font.PLAIN, tamaño));
            } catch 
                    (NumberFormatException ex) {
                         JOptionPane.showMessageDialog(null, "TAMAÑO INVALIDO.");
            }
        });

        menuEditar.add(colorFondo);
        menuEditar.add(colorLetra);
        menuEditar.add(tamañoLetra);

        menuBar.add(menuArchivo);
        menuBar.add(menuEditar);
        setJMenuBar(menuBar);
    }

    //ATAJOS EN TECLADO, COMO LOS PREDETERMINADOS (NOS AYUDAMOS CON UN EJEMPOI DE LA PAGINA https://www.javatips.net/api/javax.swing.inputmap)
    private void crearAtajos() {
        InputMap im = 
                areaTexto.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = 
                areaTexto.getActionMap();

        im.put
        (KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "DESHACER");
        im.put
        (KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "REHACER");
        im.put
        (KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK), "BUSCAR");

        am.put("DESHACER", new AbstractAction() {
            public void actionPerformed
        (ActionEvent e) {
                String estado = historial.deshacer();
                if (estado != null) areaTexto.setText(estado);
            }
        });

        am.put("REHACER", new AbstractAction() {
            public void actionPerformed
        (ActionEvent e) {
                String estado = historial.rehacer();
                if (estado != null) areaTexto.setText(estado);
            }
        });

        am.put("BUSCAR", new AbstractAction() {
            public void actionPerformed
        (ActionEvent e) {
                buscarPalabra();
            }
        });
    }

    private void buscarPalabra() {
        String palabra = 
                JOptionPane.showInputDialog("BUSCAR PALABRAA:");
        if 
                (palabra != null && !palabra.isEmpty()) {
            List<Integer> posiciones = BUSCADOR.buscarOcurrencias(areaTexto.getText(), palabra, true);
            areaTexto.getHighlighter().removeAllHighlights();

            if 
                    (!posiciones.isEmpty()) {
                Highlighter highlighter = areaTexto.getHighlighter();
                Highlighter.HighlightPainter painter = 
                        new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                for 
                        (Integer pos : posiciones) {
                    try {
                        highlighter.addHighlight(pos, pos + palabra.length(), painter);
                    } catch 
                            (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                areaTexto.setCaretPosition(posiciones.get(0));
                JOptionPane.showMessageDialog
                            (this, "PALABRA ENCONTRADA EN LA POSICIÓN " + posiciones.size());
            } else { // EN CASO DE NO LOCALIZAR LA PALABRA QUE BUSCAMOS
                JOptionPane.showMessageDialog
                            (this, "PALABRA NO ENCONTRADA ESTIMADO, VALIDE PORFA.");
            }
        }
    }

    // PARA GUARDAR ARCHIVO
    
    private void guardarArchivo() {
        JFileChooser fileChooser = 
                new JFileChooser();
        fileChooser.setDialogTitle
                    ("GUARDAR COMO ");
        fileChooser.setFileFilter
                    (new FileNameExtensionFilter("ARCHIVOS DE TEXTO (*.txt)", "txt"));
        int 
                seleccion = fileChooser.showSaveDialog(this);
        if 
                (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            if 
                    (!archivo.getName().endsWith(".txt")) {
                archivo = 
                        new File(archivo.getAbsolutePath() + ".txt");
            }

            try
                (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write(areaTexto.getText());
                JOptionPane.showMessageDialog
                            (this, "SE GUARDO SU ARCHIVO.....");
            } catch 
                    (IOException ex) {
                JOptionPane.showMessageDialog
                            (this, "ERROR AL GUARDAR EL ARCHIVO, FAVOR COMUNICARSE CON EL ING.RUBEN O ING.CARLOS.");
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EDITORDETEXTO::new);
    }
}
