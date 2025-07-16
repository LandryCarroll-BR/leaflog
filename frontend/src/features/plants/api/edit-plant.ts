import { useMutation, useQueryClient } from '@tanstack/react-query';
import { z } from 'zod';

import { api } from '@/lib/api-client';
import { MutationConfig } from '@/lib/react-query';
import { Plant } from '@/types/api';

import { getPlantsQueryOptions } from './get-plants';

export const editPlantInputSchema = z.object({
  id: z.string().min(1, 'Required'),
  name: z.string().min(1, 'Required'),
  species: z.string().min(1, 'Required'),
  wateringFrequencyInDays: z.coerce.number().min(1, 'Must be at least 1 day'),
  notes: z.string().optional(),
  lastWatered: z.coerce
    .date()
    .optional()
    .default(() => new Date()),
});

export type EditPlantInput = z.infer<typeof editPlantInputSchema>;

export const editPlant = ({
  data,
}: {
  data: EditPlantInput;
}): Promise<Plant> => {
  const { id, ...rest } = data;
  console.log('Editing plant with data:', data);
  return api.put(`/plants/${data.id}`, rest);
};

type UseEditPlantOptions = {
  mutationConfig?: MutationConfig<typeof editPlant>;
};

export const useEditPlant = ({ mutationConfig }: UseEditPlantOptions = {}) => {
  const queryClient = useQueryClient();

  const { onSuccess, ...restConfig } = mutationConfig || {};

  return useMutation({
    onSuccess: (...args) => {
      queryClient.invalidateQueries({
        queryKey: getPlantsQueryOptions().queryKey,
      });
      onSuccess?.(...args);
    },
    ...restConfig,
    mutationFn: editPlant,
  });
};
