## Style Transfer 
Recreate image or video in other painting styles 
Repo: https://github.com/lengstrom/fast-style-transfer
Pre-trained Models: https://drive.google.com/drive/folders/0B9jhaT37ydSyRk9UX0wwX3BpMzQ
```bash
conda create -n style-transfer python=2.7.9 
source activate style-transfer 
conda install -c conda-forge tensorflow=0.11.0 
conda install scipy pillow 
python evaluate.py --checkpoint <path_to_ckpt_file> --in-path <path_to_input_file> --out-path ./output_image.jpg 
```