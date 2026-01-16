//
// Created by Pavel Atanassov on 16.01.26.
//

#ifndef FILTER_H
#define FILTER_H

#include "bitmap.h"

void applyInstafix(Bitmap* bitmap);
void applyAnselFilter(Bitmap* bitmap);
void applyTestino(Bitmap* bitmap);
void applyXPro(Bitmap* bitmap);
void applyRetro(Bitmap* bitmap);
void applyBlackAndWhiteFilter(Bitmap* bitmap);
void applySepia(Bitmap* bitmap);
void applyCyano(Bitmap* bitmap);
void applyGeorgia(Bitmap* bitmap);
int applySahara(Bitmap* bitmap);
int applyHDR(Bitmap* bitmap);

#endif //FILTER_H
